import SockJS from "sockjs-client";
import Stomp from "stompjs";

const sockJS = new SockJS("http://localhost:8081/ws");
const stompClient = Stomp.over(sockJS);
stompClient.connect(
  {},
  () => console.log("connected"),
  (error) => console.log(error)
);

export default stompClient;
