package chessknights.resultsJAXB;

import lombok.extern.slf4j.Slf4j;
import util.jaxb.JAXBHelper;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class GameResult1 {


    /**
     * {@link RecordList} object creating a list to store
     * all player's data.
     */
    private RecordList recordList = new RecordList();


    public GameResult1() {

        try {
            this.recordList = JAXBHelper.fromXML(RecordList.class, new FileInputStream("DataBase.xml"));
        }catch (FileNotFoundException | JAXBException ex){
            log.error(ex.toString());
            this.recordList.setRecords(new ArrayList<>());
        }

    }

    public void createRecords(String player) {
        List<Record> records = new ArrayList<>(this.recordList.getRecords());
        List<Record> result = new ArrayList<>(records);

        System.out.println(player);

        boolean isAdded;

        for (Record record: records) {
            isAdded = false;
            System.out.println(record);

            if (result.size() == 0) {
                result.add(new Record(player, 1));
                isAdded = true;
            }
            else {
                for (int i = 0; i < result.size(); i++)
                    if (result.get(i).getPlayer().equals(player)) {
                        result.get(i).setTotalWins(result.get(i).getTotalWins() + 1);
                        isAdded = true;
                        System.out.println("Old Player");
                        break;
                    }
            }
            if (!isAdded) {
                System.out.println("New Player");
                result.add(new Record(player, 1));
            }
        }

        System.out.println(result);

        Collections.sort(result);

        this.recordList.setRecords(result);

        System.out.println("===========================" + recordList);

        try {
            JAXBHelper.toXML(this.recordList, new FileOutputStream("DataBase.xml"));
        } catch (JAXBException | FileNotFoundException e) {
            log.error(e.toString());
        }
    }
    public List<Record> getGameResults() {
        return recordList.getRecords();
    }
}
