package sameeh.com.nbapp.Models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by samee on 4/30/2018.
 */

public class TeamPlayersList implements Serializable, List<Player> {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @NonNull
    @Override
    public Iterator<Player> iterator() {
        return null;
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @NonNull
    @Override
    public <T> T[] toArray(@NonNull T[] ts) {
        return null;
    }

    @Override
    public boolean add(Player player) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean addAll(@NonNull Collection<? extends Player> collection) {
        return false;
    }

    @Override
    public boolean addAll(int i, @NonNull Collection<? extends Player> collection) {
        return false;
    }

    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        return false;
    }

    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Player get(int i) {
        return null;
    }

    @Override
    public Player set(int i, Player player) {
        return null;
    }

    @Override
    public void add(int i, Player player) {

    }

    @Override
    public Player remove(int i) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @NonNull
    @Override
    public ListIterator<Player> listIterator() {
        return null;
    }

    @NonNull
    @Override
    public ListIterator<Player> listIterator(int i) {
        return null;
    }

    @NonNull
    @Override
    public List<Player> subList(int i, int i1) {
        return null;
    }
}
