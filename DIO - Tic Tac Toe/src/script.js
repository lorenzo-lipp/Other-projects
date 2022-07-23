const gameBoard = () => {
    return {game: ["", "", "", "", "", "", "", "", ""]}
}

const player = (text) => {
    const piece = text
    return {piece: text, name: "Player " + text}
}

var turn = 0
var players = {player1: player("X"), player2: player("O")}
var playerTurn = players[Object.keys(players)[turn % 2]]
var board = gameBoard()
var over = false;

function render(x) {
    if (board.game[x] == "") {
        board.game[x] = playerTurn.piece
        turn++
        playerTurn = players[Object.keys(players)[turn % 2]]
        check()
        if (over == false) {document.getElementById("message").innerHTML = `<h1>${playerTurn.name} turn</h1>`}
        else {turn++
            playerTurn = players[Object.keys(players)[turn % 2]]
            document.getElementById("message").innerHTML = `<h1>Winner: ${playerTurn.name}</h1>`
        }
        document.getElementById("game").innerHTML = `<table>
                                                        <tr>
                                                            <td><a onclick="render(0)">${board.game[0]}</a></td>
                                                            <td><a onclick="render(1)">${board.game[1]}</a></td>
                                                            <td><a onclick="render(2)">${board.game[2]}</a></td>
                                                        </tr>
                                                        <tr>
                                                            <td><a onclick="render(3)">${board.game[3]}</a></td>
                                                            <td><a onclick="render(4)">${board.game[4]}</a></td>
                                                            <td><a onclick="render(5)">${board.game[5]}</a></td>
                                                        </tr>
                                                        <tr>
                                                            <td><a onclick="render(6)">${board.game[6]}</a></td>
                                                            <td><a onclick="render(7)">${board.game[7]}</a></td>
                                                            <td><a onclick="render(8)">${board.game[8]}</a></td>
                                                        </tr>
                                                    </table>`
    }
}

function check() {
    for (var i = 0; i < 7; i = i+3) {
        if (board.game[i] != "" && board.game[i] == board.game[i+1] && board.game[i] == board.game[i+2]) {gameOver()}
    }
    for (var i = 0; i < 4; i++) {
        if (board.game[i] != "" && board.game[i] == board.game[i+3] && board.game[i] == board.game[i+6]) {gameOver()}
    }
    if (board.game[0] != "" && board.game[0] == board.game[4] && board.game[0] == board.game[8]) {gameOver()}
    if (board.game[2] != "" && board.game[2] == board.game[4] && board.game[2] == board.game[6]) {gameOver()}
}

function gameOver() {
    for (var i in board.game) {
        if (board.game[i] == "") {board.game[i] = "-"}
    }
    over = true
}

function changeNames() {
    let px = document.getElementById("px").value
    let po = document.getElementById("po").value
    if (px != "") {players.player1.name = px}
    if (po != "") {players.player2.name = po}
    if (over == false) {document.getElementById("message").innerHTML = `<h1>${playerTurn.name} turn</h1>`}
}

function restart() {
    board = gameBoard()
    turn = 0
    playerTurn = players[Object.keys(players)[turn % 2]]
    over = false
    document.getElementById("message").innerHTML = `<h1>${playerTurn.name} turn</h1>`
    document.getElementById("game").innerHTML = `<table>
                                                    <tr>
                                                        <td><a onclick="render(0)"></a></td>
                                                        <td><a onclick="render(1)"></a></td>
                                                        <td><a onclick="render(2)"></a></td>
                                                    </tr>
                                                    <tr>
                                                        <td><a onclick="render(3)"></a></td>
                                                        <td><a onclick="render(4)"></a></td>
                                                        <td><a onclick="render(5)"></a></td>
                                                    </tr>
                                                    <tr>
                                                        <td><a onclick="render(6)"></a></td>
                                                        <td><a onclick="render(7)"></a></td>
                                                        <td><a onclick="render(8)"></a></td>
                                                    </tr>
                                                </table>`
}