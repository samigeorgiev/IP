import React, { useCallback, useEffect, useRef, useState } from "react";
import CanvasDraw from "react-canvas-draw";
import { useParams } from "react-router-dom";
import * as uuid from "uuid";
import stompClient from "../stompClient";

const Room = () => {
  const [userId, setUserId] = useState();
  const [mode, setMode] = useState("GUESS");
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");
  const subscriptions = useRef([]);
  const canvas = useRef();
  const params = useParams();

  useEffect(() => {
    setUserId(uuid.v4());
  }, []);

  const addMessage = useCallback(
    (message) => {
      setMessages([...messages, message]);
    },
    [messages]
  );

  const sendMessageHandler = (action, message) => {
    if (!stompClient) {
      return;
    }
    stompClient.send(
      `/app/${action}`,
      {},
      JSON.stringify({
        roomId: params.roomId,
        ...message,
      })
    );
  };

  const setDrawerHandler = useCallback(
    (message) => {
      if (message.userId !== userId.toString()) {
        return;
      }
      setMode("CHOOSE_WORD");
      addMessage(`Choose among ${message.possibleWords}`);
    },
    [userId, addMessage]
  );

  const updateCanvasHandler = useCallback(
    (message) => {
      if (mode !== "GUESS") {
        return;
      }
      canvas.current.loadSaveData(message.canvas);
    },
    [mode]
  );

  const receiveMessageHandler = useCallback(
    (message) => {
      addMessage(message.message);
    },
    [addMessage]
  );

  const startGuessingHandler = useCallback(
    (message) => {
      if (mode === "CHOOSE_WORD") {
        setMode("DRAW");
      } else {
        addMessage("Start guessing");
      }
    },
    [addMessage, mode]
  );

  const connectHandler = useCallback(() => {
    if (!userId) {
      return;
    }
    subscriptions.current = [
      stompClient.subscribe(`/user/${params.roomId}/set-drawer`, (message) =>
        setDrawerHandler(JSON.parse(message.body))
      ),
      stompClient.subscribe(`/user/${params.roomId}/update-canvas`, (message) =>
        updateCanvasHandler(JSON.parse(message.body))
      ),
      stompClient.subscribe(`/user/${params.roomId}/messages`, (message) =>
        receiveMessageHandler(JSON.parse(message.body))
      ),
      stompClient.subscribe(
        `/user/${params.roomId}/start-guessing`,
        (message) => startGuessingHandler(JSON.parse(message.body))
      ),
    ];
    stompClient.send(
      `/app/register`,
      {},
      JSON.stringify({
        roomId: params.roomId,
        userId: userId,
      })
    );
  }, [
    params,
    updateCanvasHandler,
    setDrawerHandler,
    userId,
    receiveMessageHandler,
    startGuessingHandler,
  ]);

  useEffect(() => {
    connectHandler();
    return () => {
      subscriptions.current.forEach((x) => x.unsubscribe());
    };
  }, [connectHandler]);

  const canvasChangeHandler = (canvas) => {
    if (mode !== "DRAW") {
      return;
    }
    sendMessageHandler("set-canvas", { canvas: canvas.getSaveData() });
  };

  const inputHandler = () => {
    switch (mode) {
      case "CHOOSE_WORD":
        sendMessageHandler("set-word", { word: input });
        addMessage(`You: ${input}`);
        setInput("");
        break;
      case "GUESS":
        sendMessageHandler("guess", { userId, guess: input });
        addMessage(`You: ${input}`);
        setInput("");
        break;
      default:
        throw new Error();
    }
  };

  return (
    <>
      <CanvasDraw
        disabled={mode !== "DRAW"}
        onChange={canvasChangeHandler}
        ref={canvas}
      />
      <ul>
        {messages.map((x) => (
          <li key={x}>{x}</li>
        ))}
      </ul>
      <input value={input} onChange={(event) => setInput(event.target.value)} />
      <button onClick={inputHandler}>Submit</button>
    </>
  );
};

export default Room;