import Game from './components/Game'
import 'bootstrap/dist/css/bootstrap.min.css';

const App = () => {
  document.title = "Tic Tac Toe V0.2.1"
  
  return (
    <div className="App">
      <Game></Game>
    </div>
  );
}

export default App;
