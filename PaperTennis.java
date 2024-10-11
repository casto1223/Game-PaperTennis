// Castor Chen
// 10/10/2023

import java.util.*;

// A class to represent a game of Paper Tennis that implements the
// AbstractStrategyGame interface
public class PaperTennis implements AbstractStrategyGame {
    private int ballPos;
    private boolean isP1Turn;
    private int p1Points;
    private int p2Points;
    private int p1OverallScore;
    private int p2OverallScore;
    private int roundNum;
    private int p1Bid;
    private int p2Bid;
    private boolean doneBidding;

    // Constructs a new PaperTennis game
    public PaperTennis (){
        ballPos = 0;
        isP1Turn = true;
        p1Points = 50;
        p2Points = 50;
        p1OverallScore = 0;
        p2OverallScore = 0;
        roundNum = 1;
    }

    // returns a String containing instructions on how to play the game 
    public String instructions(){
        String ans = "Paper Tennis is an (abstract) strategic game for two players.\n";
        ans += "The game field consists of 6 fields and a centre line (0).\n";
        ans += "These are called (-3, -2, -1, 0, 1, 2, 3), with negative numbers belonging to player 1,\n"; 
        ans += "positive to player 2. At start, the ball is at the centre line (0).\n";
        ans += "Both players start with the same amount of points (e.g. 50 points).\n"; 
        ans += "For each round, both players bid a number x (0<= x <=their total points),\n"; 
        ans += "and the ball is moved towards the player with the smallest number.\n";
        ans += "Each round, the players' number is decreased by their bid.\n";
        ans += "The game is over when either both players have 0 points\n";
        ans += "or the ball reaches 3 (Player 1 wins) or -3 (Player 2 wins).\n";
        ans += "If the ball did not reach 3 or -3 , Player 1 wins if\n";
        ans += "the ball is on either 1 or 2, Player 2 wins if the ball is on either\n";
        ans += "-1 or -2, and it is a tie if ball is still at 0 after both Player 1 and Player 2\n";
        ans += "is at 0 points.\n"; 
        ans += "This game is played 3 times total, and overall winner is determined\n";
        ans += "by whoever has the most overall points after 3 games is the final winner,\n";
        ans += "a player's overall points increases\n";
        ans += "by 2 if the game is won with the ball being in 3 or -3, 1 if it is won any other way,\n";
        ans += "by 0 if the game is a tie.\n";
        return ans;
    } 

    // returns a String representation of the current game state
    public String toString(){
        String ans = "This is round " + this.roundNum + " of 3 rounds.\n";
        ans += "The ball is at position " + this.ballPos + ".\n";
        ans += "Player 1 has " + this.p1Points + " points left in this round.\n";
        ans += "Player 2 has " + this.p2Points + " points left in this round.\n";
        ans += "Player 1 has an overall score of " + this.p1OverallScore + ".\n";
        ans += "Player 2 has an overall score of " + this.p2OverallScore + ".\n";
        return ans; 
    }

    /**
    * Returns true if the game has ended, and false otherwise.Also increment 
    * the overall score of the players accordingly.
    */
    public boolean isGameOver(){
        if (((ballPos == 3 || ballPos == -3) || (p1Points == 0 && p2Points == 0)) && roundNum < 4) {
            if (ballPos == 3){
                p1OverallScore += 2;
            } else if (ballPos == -3){
                p2OverallScore += 2;
            } else if (ballPos == 2 || ballPos == 1){
                p1OverallScore ++;
            } else if (ballPos == -2 || ballPos == -1){
                p2OverallScore ++;
            }
            ballPos = 0;
            isP1Turn = true;
            p1Points = 50;
            p2Points = 50;
            roundNum++;
        }
        return roundNum == 4;
    }

    /**
    * Returns the index of the player who has won the game,
    * or -1 if the game is not over, 0 if tie game.
    */
    public int getWinner() {
        if (this.roundNum < 4){
            return -1;
        } else {
            if (p1OverallScore > p2OverallScore){
                return 1;
            } else if (p1OverallScore < p2OverallScore){
                return 2;
            } else {
                return 0;
            }
        }
    }

    /**
    * Returns the index of the player who will take the next turn.
    * If the game is over, returns -1.
    */
    public int getNextPlayer(){
        if (isGameOver()) {
            return -1;
        }
        return isP1Turn ? 1 : 2;
    }

    /**
    * Takes input from the parameter to specify the move the player
    * with the next turn wishes to make, then executes that move. 
    * If any part of the move is illegal, throws an IllegalArgumentException.
    */
    public void makeMove(Scanner input){
        System.out.println(this.toString());
        if (isP1Turn) {
            System.out.print("Player 1 bids: ");
            p1Bid = input.nextInt();
            if (p1Bid > p1Points){
                throw new IllegalArgumentException("You bid more than you could!");
            }
            p1Points -= p1Bid;
            System.out.println();
            doneBidding = false;
        } else {
            System.out.print("Player 2 bids: ");
            p2Bid = input.nextInt();
            if (p2Bid > p2Points){
                throw new IllegalArgumentException("You bid more than you could!");
            }
            p2Points -= p2Bid;
            System.out.println();
            doneBidding = true;
        }
        if (doneBidding){
            if (p1Bid > p2Bid){
                if (ballPos == -1){
                    ballPos = 1;
                } else {
                    ballPos ++;
                }
            } else if (p1Bid < p2Bid){
                if (ballPos == 1){
                    ballPos = -1;
                } else {
                    ballPos --;
                }
            } else {
                ballPos = ballPos;
            }
        }
        isP1Turn = !isP1Turn;
    }
}
