<!DOCTYPE html>
<html lang="en">
<head>
    <?php require 'partials/head.php' ?>
    <title>Rules | Solidaire</title>
    <link rel="preconnect" href="https://fonts.gstatic.com">
</head>
<body>
<header>
    <?php require 'partials/nav.php' ?>
</header>

<div class="background-rules">
    <div class="rules-page-container">
        <article><h1>Solitaire</h1>
            <h2>Goal of the game</h2>
            <p>The object of the game is to stay with <strong>as few marbles as possible</strong></p>
            <h2>How to play ?</h2>
            <p>The game begins with a board with marbles.<br>
                You can move a marble by passing it over another, if the other is on 
                <strong>one of the 4 spaces around</strong> and there is  <strong>an empty space on the other side.</strong><br>
            </p>
            <p>
                The first ball "jumps" over the second and joins the empty square.<br>
                The second ball is then <strong>removed from the board.</strong>
            </p>
            <p>The game ends when no ball can be moved. </p>
            <h2>Exemple :</h2>
            <div class="exempleRules">
                <img src="img/6billes1.png" width="200px">
                <i class="fas fa-arrow-right fa-6x"></i>
                <img src="img/6billes2.png" width="200px">
            </div>
            <br>
            <p>
            In this condition, only ball <strong>A</strong> can move, and ball <strong>B</strong> is removed from the board.
            </p>
        </article>
    </div>
</div>
<footer>
    <?php require 'partials/footer.php' ?>
</footer>
</body>