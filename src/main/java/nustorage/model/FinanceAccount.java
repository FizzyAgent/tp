package nustorage.model;


import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nustorage.commons.core.index.Index;
import nustorage.model.record.FinanceRecord;


// public class FinanceAccount implements Iterable<FinanceRecord> {
public class FinanceAccount implements Iterable<FinanceRecord>, ReadOnlyFinanceAccount {

    private final ObservableList<FinanceRecord> internalList = FXCollections.observableArrayList();
    private final ObservableList<FinanceRecord> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    public FinanceAccount() {

    }


    @Override
    public void addRecord(FinanceRecord record) {
        internalList.add(record);
    }


    @Override
    public void setFinanceRecord(FinanceRecord target, FinanceRecord newRecord) {
        int index = internalList.indexOf(target);
        internalList.remove(index);
        internalList.add(index, newRecord);
    }


    @Override
    public boolean hasRecord(FinanceRecord record) {
        return this.internalList.contains(record);
    }


    /**
     * Removes the finance record with the corresponding index
     *
     * @param targetIndex Index of finance record to be removed
     * @return Optional containing removed finance record if index is valid, else an empty optional
     */
    @Override
    public Optional<FinanceRecord> removeRecord(Index targetIndex) {

        if (targetIndex.getZeroBased() >= internalList.size()) {
            return Optional.empty();
        }

        return Optional.of(internalList.remove(targetIndex.getZeroBased()));
    }


    @Override
    public int count() {
        return internalList.size();
    }


    /**
     * Returns the net transaction amount of all finance records
     *
     * @return Net transaction amount of all finance records
     */
    @Override
    public double netProfit() {
        return internalList.stream()
                .mapToDouble(FinanceRecord::getAmount)
                .sum();
    }


    @Override
    public List<FinanceRecord> filterRecords(Predicate<FinanceRecord> filter) {
        return internalList.stream().filter(filter).collect(Collectors.toList());
    }


    @Override
    public boolean isEmpty() {
        return internalList.isEmpty();
    }


    @Override
    public ObservableList<FinanceRecord> getFinanceList() {
        return internalList;
    }


    @Override
    public ObservableList<FinanceRecord> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }


    @Override
    public Iterator<FinanceRecord> iterator() {
        return internalList.iterator();
    }


    @Override
    public String toString() {
        return internalList.stream()
                .map(FinanceRecord::toString)
                .collect(Collectors.joining("\n"));
    }

}
