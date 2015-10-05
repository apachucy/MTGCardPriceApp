package unii.mtg.cardprice.mtgcardpriceapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import unii.mtg.cardprice.mtgcardpriceapp.config.FragmentConfig;
import unii.mtg.cardprice.mtgcardpriceapp.database.Card;
import unii.mtg.cardprice.mtgcardpriceapp.fragments.CardPriceFragment;
import unii.mtg.cardprice.mtgcardpriceapp.fragments.CardPriceListFragment;
import unii.mtg.cardprice.mtgcardpriceapp.fragments.ICardList;
import unii.mtg.cardprice.mtgcardpriceapp.fragments.ICardPriceDraftList;
import unii.mtg.cardprice.mtgcardpriceapp.fragments.NavigationDrawerFragment;

public class CardPriceActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ICardPriceDraftList, ICardList {


    private ArrayList<Card> mCardList = new ArrayList<>();
    private Card mSearchedCard = new Card();
    private ArrayList<Card> mDraftCardList = new ArrayList<>();


    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */

    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_price);
        // ButterKnife.bind(this);
        openHelper(this);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        handleIntent(getIntent());

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            case FragmentConfig.ID_CARD_PRICE_FRAGMENT:
                fragment = new CardPriceFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(unii.mtg.cardprice.mtgcardpriceapp.config.Bundle.CARD_BUNDLE, mSearchedCard);
                fragment.setArguments(bundle);
                break;
            case FragmentConfig.ID_CARD_PRICE_DRAFT_LIST_FRAGMENT:
                fragment = new CardPriceListFragment();
                break;
            default:
                fragment = new CardPriceListFragment();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //drawer is close - show CARD PRICE MENU
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.card_price, menu);
            restoreActionBar();
            // Associate searchable configuration with the SearchView
            MenuItem searchItem = menu.findItem(R.id.action_search);
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = null;
            if (searchItem != null) {
                searchView = (SearchView) searchItem.getActionView();
            }
            if (searchView != null) {
                searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            }
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //TODO: Make a request for a card !!
            Toast.makeText(CardPriceActivity.this, query, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public ArrayList<Card> getDraftCardList() {
        return mDraftCardList;
    }

    @Override
    public ArrayList<Card> getCardList() {
        return mCardList;
    }


}
