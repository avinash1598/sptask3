package com.example.avinash.sptask3;

import java.util.List;

/**
 * Created by AVINASH on 7/5/2016.
 */
public class SearchResults {
    private List<Search> Search;

    public List<Search> getSearch() {
        return Search;
    }

    public void setSearch(List<Search> search) {
        Search = search;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchResults that = (SearchResults) o;

        return !(Search != null ? !Search.equals(that.Search) : that.Search != null);

    }

    @Override
    public int hashCode() {
        return Search != null ? Search.hashCode() : 0;
    }
}
