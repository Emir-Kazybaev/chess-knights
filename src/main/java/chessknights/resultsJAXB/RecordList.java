package chessknights.resultsJAXB;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@XmlRootElement(name = "records")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecordList {

    /**
     * List to store values of record class.
     */
    @XmlElement(name = "record")
    private List<Record> records;

}
