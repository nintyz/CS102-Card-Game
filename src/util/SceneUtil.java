/**
 * util.SceneUtil.java
 * 
 * @version 1.0
 * 
 * @author Aaron, Andre, En Ting, Gerald, Xavier
 * 
 * Last modified: 31 Mar 2024
 */


package util;

import java.text.DecimalFormat;

import model.game.Player;


/**
 * 
 * This class contains attibutes and methods aiding in the formatting of scene elements
 * 
 */
public class SceneUtil {

    public static final String MAIN_SCENE = "resources/view/match-cards.fxml";
    public static final String END_SCENE = "resources/view/end-game.fxml";

    // creates new DecimalFormat Object of the specified pattern
    private static final DecimalFormat DEC_FORMAT = new DecimalFormat("#.##");

    public static DecimalFormat getDecFormat() {
        return DEC_FORMAT;
    }
    
    public static String getPlayerScoreText(Player player, double score1, double score2) {
        return player.getPlayerId() == 0 ? formatScore(score1) : formatScore(score2);
    }

    // formats score based on the pre-set decimal format 
    public static String formatScore(double score) {
        return String.valueOf(DEC_FORMAT.format(score));
    }
}
