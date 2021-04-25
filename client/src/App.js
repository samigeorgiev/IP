import {Route, Switch} from 'react-router-dom';
import './App.css';
import Room from "./pages/Room";
import Rooms from "./pages/Rooms";

const App = () => {
    return <Switch>
        <Route path="/rooms/:roomId" exact>
            <Room/>
        </Route>
        <Route path="/rooms" exact>
            <Rooms/>
        </Route>
    </Switch>
}

export default App;