package chessknights.resultsJAXB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.time.Duration;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Record implements Comparable<Record>{

    /**
     * The name of the player.
     */
    private String player;


    /**
     * Number of total wins.
     */
    private int totalWins;


    /**
     * For comparing objects of type Record.
     * @param o passing instance of Record object to compare.
     * @return positive value if totalWins field of first object, more than of the second.
     * @return 0 value if totalWins field of first and second object are same.
     * @return positive value if totalWins field of second object, more than of the first.
     */
    @Override
    public int compareTo(Record o) {
        return Integer.compare(o.totalWins, totalWins);
    }
}
