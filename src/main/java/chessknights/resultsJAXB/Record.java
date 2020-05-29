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


    private int totalWins;


    @Override
    public int compareTo(Record o) {
        return Integer.compare(o.totalWins, totalWins);
    }
}
