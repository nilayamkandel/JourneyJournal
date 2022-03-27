package np.com.neelayamkandel.journeyjournal.dao.home;

import java.io.Serializable;

public class JourneyRecyclerDao implements Serializable {
    public JourneyRecyclerDao(String uuid, JourneyDao journey) {
        Uuid = uuid;
        this.journey = journey;
    }

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        Uuid = uuid;
    }

    public JourneyDao getJourney() {
        return journey;
    }

    public void setJourney(JourneyDao journey) {
        this.journey = journey;
    }

    private String Uuid;
    private JourneyDao journey;
}
