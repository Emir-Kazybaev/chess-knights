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


    /**
     * Checks if DataBase records already exists, if not set a file which keeps the players records to be empty.
     */
    public GameResult1() {
        try {
            this.recordList = JAXBHelper.fromXML(RecordList.class, new FileInputStream("DataBase.xml"));
        }catch (FileNotFoundException | JAXBException ex){
            log.error(ex.toString());
            this.recordList.setRecords(new ArrayList<>());
        }
    }

    /**
     *
     * @param player obtain nickname as a string of a player who won the match.
     * If database is empty - adding a new player to database with 1 victory.
     * If database contain players then checks if if the player who won the match among the list,
     *               if player is there, increase value of wins by one.
     * Otherwise, adding a new player to database with 1 victory.
     */
    public void createRecords(String player) {
        List<Record> records = new ArrayList<>(this.recordList.getRecords());
        List<Record> result = new ArrayList<>(records);
        boolean isAdded;
        for (Record record: records) {
            isAdded = false;
            System.out.println(record);
            if (records.size() == 0) {
                records.add(new Record(player, 1));
                isAdded = true;
                log.info("Added {} to database with one win",player);
            }
            else {
                for (int i = 0; i < records.size(); i++)
                    if (record.getPlayer().equals(player)) {
                        records.get(i).setTotalWins(result.get(i).getTotalWins() + 1);
                        isAdded = true;
                        log.info("Added one to wins of {}",player);
                        break;
                    }
            }
            if (!isAdded) {
                records.add(new Record(player, 1));
                log.info("Added {} to database with one win",player);
                break;
            }
        }
        Collections.sort(records);
        this.recordList.setRecords(records);
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
