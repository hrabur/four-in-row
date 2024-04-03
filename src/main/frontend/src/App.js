import Board from './Board';
import './App.css';
import { useState } from 'react';

function App() {
  const [game, setGame] = useState(null);

  const handleStartNewGame = async () => {
    const response = await fetch("/games", {
      method: 'POST',
    });
    const game = await response.json();
    setGame(game);
  };

  const handleMakeMove = async col => {
    await fetch(`/games/${game.gameId}/moves`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        "player": game.turn,
        "column": col
      })
    });

    const response = await fetch(`/games/${game.gameId}`);
    const freshGame = await response.json();
    setGame(freshGame);
  };

  return (
    <div className="App">
      <h1>Web Programming Connect 4</h1>
      {/* TODO: When game is over display start new game button */}
      {!game && <button onClick={handleStartNewGame}>Start new game</button>}
      {game && <Board 
        game={game} 
        onMakeMove={handleMakeMove} />
      }
    </div>
  );
}

export default App;
