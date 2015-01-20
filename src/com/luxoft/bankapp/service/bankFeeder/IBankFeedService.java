package com.luxoft.bankapp.service.bankFeeder;

import java.util.List;
import java.util.Map;

/**
 * Created by Makarov Denis on 19.01.2015.
 */
public interface IBankFeedService {
    void parseFeed(String feed);
    void loadFeeds(String folder);
}
