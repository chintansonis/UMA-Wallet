package com.umawallet.custom.customspinner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.umawallet.R;
import com.umawallet.api.responsepojos.AddressMaster;
import com.umawallet.api.responsepojos.TokenBalance;
import com.umawallet.helper.AppConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchableListDialog extends DialogFragment implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String ITEMS = "mOrignalitems";

    private ListItemAdapter listAdapter;

    private ListView _listViewItems;

    private SearchableItem _searchableItem;

    private OnSearchTextChanged _onSearchTextChanged;

    private SearchView _searchView;

    private String _strTitle;

    private String _strPositiveButtonText;

    private DialogInterface.OnClickListener _onClickListener;

    public SearchableListDialog() {

    }

    public static SearchableListDialog newInstance(List items) {
        SearchableListDialog multiSelectExpandableFragment = new
                SearchableListDialog();

        Bundle args = new Bundle();
        args.putSerializable(ITEMS, (Serializable) items);

        multiSelectExpandableFragment.setArguments(args);

        return multiSelectExpandableFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Getting the layout inflater to inflate the view in an alert dialog.
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        // Crash on orientation change #7
        // Change Start
        // Description: As the instance was re initializing to null on rotating the device,
        // getting the instance from the saved instance
        if (null != savedInstanceState) {
            _searchableItem = (SearchableItem) savedInstanceState.getSerializable("item");
        }
        // Change End

        View rootView = inflater.inflate(R.layout.searchable_list_dialog, null);
        setData(rootView);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(rootView);

        String strPositiveButton = _strPositiveButtonText == null ? "CLOSE" : _strPositiveButtonText;
        alertDialog.setPositiveButton(strPositiveButton, _onClickListener);

        String strTitle = _strTitle == null ? "Select Item" : _strTitle;
        alertDialog.setTitle(strTitle);


        final AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN);
        return dialog;
    }

    // Crash on orientation change #7
    // Change Start
    // Description: Saving the instance of searchable item instance.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("item", _searchableItem);
        super.onSaveInstanceState(outState);
    }
    // Change End

    public void setTitle(String strTitle) {
        _strTitle = strTitle;
    }

    public void setPositiveButton(String strPositiveButtonText) {
        _strPositiveButtonText = strPositiveButtonText;
    }

    public void setPositiveButton(String strPositiveButtonText, DialogInterface.OnClickListener onClickListener) {
        _strPositiveButtonText = strPositiveButtonText;
        _onClickListener = onClickListener;
    }

    public void setOnSearchableItemClickListener(SearchableItem searchableItem) {
        this._searchableItem = searchableItem;
    }

    public void setOnSearchTextChangedListener(OnSearchTextChanged onSearchTextChanged) {
        this._onSearchTextChanged = onSearchTextChanged;
    }

    private void setData(View rootView) {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context
                .SEARCH_SERVICE);
        _searchView = (SearchView) rootView.findViewById(R.id.search);
        int id = _searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) _searchView.findViewById(id);
        textView.setHintTextColor(Color.BLACK);
        textView.setTextColor(Color.BLACK);
        _searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName
                ()));
        _searchView.setIconifiedByDefault(false);
        _searchView.setOnQueryTextListener(this);
        _searchView.setOnCloseListener(this);
        _searchView.clearFocus();
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(_searchView.getWindowToken(), 0);


        List items = (List) getArguments().getSerializable(ITEMS);

        _listViewItems = (ListView) rootView.findViewById(R.id.listItems);

        //create the adapter by passing your ArrayList data
        listAdapter = new ListItemAdapter(items);
        //attach the adapter to the list
        _listViewItems.setAdapter(listAdapter);

        _listViewItems.setTextFilterEnabled(true);

        _listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(_searchView.getWindowToken(), 0);
                Object obj = listAdapter.getItem(position);
                if (obj instanceof AddressMaster) {
                    AddressMaster addressMaster = (AddressMaster) obj;
                    if (!addressMaster.getAddressId().equalsIgnoreCase(AppConstants.DefaultID)) {
                        _searchableItem.onSearchableItemClicked(listAdapter.getItem(position), position);
                        getDialog().dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Invalid Selection", Toast.LENGTH_SHORT).show();
                    }
                } else if (obj instanceof TokenBalance) {
                    TokenBalance addressMaster = (TokenBalance) obj;
                    if (!addressMaster.getTokenId().equalsIgnoreCase(AppConstants.DefaultID)) {
                        _searchableItem.onSearchableItemClicked(listAdapter.getItem(position), position);
                        getDialog().dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Invalid Selection", Toast.LENGTH_SHORT).show();
                    }
                } else if (obj instanceof String) {
                    String txt = (String) obj;
                    _searchableItem.onSearchableItemClicked(listAdapter.getItem(position), position);
                    getDialog().dismiss();
                }
            }
        });
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        _searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
//        listAdapter.filterData(s);
        if (TextUtils.isEmpty(s)) {
//                _listViewItems.clearTextFilter();
            ((ListItemAdapter) _listViewItems.getAdapter()).getFilter().filter(null);
        } else {
            ((ListItemAdapter) _listViewItems.getAdapter()).getFilter().filter(s);
        }
        if (null != _onSearchTextChanged) {
            _onSearchTextChanged.onSearchTextChanged(s);
        }
        return true;
    }

    public interface SearchableItem<T> extends Serializable {
        void onSearchableItemClicked(T item, int position);
    }

    public interface OnSearchTextChanged {
        void onSearchTextChanged(String strText);
    }

    private static class ListItemAdapter extends BaseAdapter implements Filterable {
        List mOrignalitems;
        List items;

        public ListItemAdapter(List Orignalitems) {
            this.mOrignalitems = Orignalitems;
            items = Orignalitems;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            Object object = items.get(position);
            if (object instanceof AddressMaster) {
                AddressMaster countryModel = (AddressMaster) object;
                textView.setText(countryModel.getWalletNickName());
            }else if (object instanceof TokenBalance) {
                TokenBalance countryModel = (TokenBalance) object;
                textView.setText(countryModel.getTokenName());
            } else if (getItem(position) instanceof String) {
                textView.setText(String.format("%s", getItem(position)));
            }
            return view;
        }


        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    ArrayList<Object> filteredArrList = new ArrayList<>();

                    if (constraint == null || constraint.length() == 0) {
                        // set the Original result to return
                        results.count = mOrignalitems.size();
                        results.values = mOrignalitems;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (Object obj : mOrignalitems) {
                            if (obj instanceof AddressMaster) {
                                AddressMaster countryModel = (AddressMaster) obj;
                                String data = countryModel.getWalletNickName().toLowerCase();
                                if (data.startsWith(constraint.toString())) {
                                    filteredArrList.add(obj);
                                }
                            } else if (obj instanceof TokenBalance) {
                                TokenBalance countryModel = (TokenBalance) obj;
                                String data = countryModel.getTokenName().toLowerCase();
                                if (data.startsWith(constraint.toString())) {
                                    filteredArrList.add(obj);
                                }
                            } else if (obj instanceof String) {
                                String data = (String) obj;
                                if (data.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                                    filteredArrList.add(obj);
                                }

                            }

                        }
                        results.values = filteredArrList;
                        results.count = filteredArrList.size();
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    items = (List) results.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    }
}
