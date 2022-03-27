package np.com.neelayamkandel.journeyjournal.model.home;

import np.com.neelayamkandel.journeyjournal.dao.helper.SuccessHelper;
import np.com.neelayamkandel.journeyjournal.dao.home.JourneyDao;

public class JourneyModel extends SuccessHelper {
    public JourneyDao getDao() {
        return dao;
    }

    public void setDao(JourneyDao dao) {
        this.dao = dao;
    }

    private JourneyDao dao;
    public JourneyModel(boolean isSuccess, String message) {
        super(isSuccess, message);
    }

    public JourneyModel(boolean isSuccess) {
        super(isSuccess);
    }
}
