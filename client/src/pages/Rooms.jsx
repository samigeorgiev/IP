import React, { useCallback, useEffect, useState } from "react";
import { useHistory } from "react-router-dom";

const Rooms = (props) => {
  const [rooms, setRooms] = useState([]);
  const history = useHistory();

  const fetchRooms = useCallback(() => {
    fetch(process.env.REACT_APP_API_BASE_URL + "/rooms")
      .then((response) => response.json())
      .then((data) => {
        setRooms(data.rooms);
      });
  }, []);

  useEffect(() => {
    fetchRooms();
  }, [fetchRooms]);

  const createRoom = () => {
    fetch(process.env.REACT_APP_API_BASE_URL + "/rooms", {
      method: "post",
    }).then(() => {
      fetchRooms();
    });
  };

  const selectRoom = (id) => {
    history.push(`/rooms/${id}`);
  };

  return (
    <>
      <ul>
        {rooms.map((room) => (
          <button onClick={() => selectRoom(room.id)} key={room.id}>
            Room {room.id}
          </button>
        ))}
      </ul>
      <button onClick={createRoom}>Create room</button>
    </>
  );
};

export default Rooms;
