import { useState } from "react";
import Pool from "./Pool";
import "./Board.css";

const Board = ({ game, onMakeMove }) => {
  const [hoverCol, setHoverCol] = useState(-1);

  const handleHover = (col) => {
    setHoverCol(col);
  }

  const { board, turn, winner, gameOver } = game;

  return (
    <div className="Board">
      {gameOver && <h2 className="Board_Message">
          Game is Over! 
          {winner && <>The winner is 
          <Pool player={winner} /></>}
      </h2>}
      {!gameOver && <div className="Board_Selector">
        {Array(board[0].length).fill(0).map((_, i) => (
          <Pool key={i} player={hoverCol === i ? turn : null} />
        ))}
      </div>}
      <div className="Board_Grid">
        {board.toReversed().map((row, i) => (
          <div key={i} className="Board_Row">
            {row.map((cell, j) => (
              <Pool
                key={j}
                player={cell}
                placeholder={true}
                onClick={() => onMakeMove(j)}
                onMouseOver={() => handleHover(j)}
                onMouseOut={() => handleHover(-1)}
              />
            ))}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Board;