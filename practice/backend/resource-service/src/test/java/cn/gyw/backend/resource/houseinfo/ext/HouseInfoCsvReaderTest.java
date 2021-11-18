package cn.gyw.backend.resource.houseinfo.ext;

import cn.gyw.backend.resource.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class HouseInfoCsvReaderTest extends AbstractTest {

    @Autowired
    private HouseInfoCsvReader houseInfoCsvReader;

    @Test
    public void readAndSaveDB() {
        houseInfoCsvReader.readAndSaveDB();
    }
}