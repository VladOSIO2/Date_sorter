package org.assignment.datesorter;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class IDateSorterImpl implements IDateSorter {
  /**
   * Method for sorting the list of dates in the next way:
   * Firstly, dates with an 'r' in the month, sorted ascending,
   * then dates without an 'r' in the month, sorted descending.
   *
   * @param unsortedDates - an unsorted list of dates
   * @return ordered collection of properly sorted dates
   */
  @Override
  public Collection<LocalDate> sortDates(List<LocalDate> unsortedDates) {
    Map<Boolean, List<LocalDate>> partitionedDates = unsortedDates.stream()
        .collect(Collectors.partitioningBy(this::containsRInMonth));

    List<LocalDate> datesWithR = partitionedDates.get(true);
    List<LocalDate> datesWithoutR = partitionedDates.get(false);

    datesWithR.sort(LocalDate::compareTo);
    datesWithoutR.sort(Comparator.reverseOrder());

    datesWithR.addAll(datesWithoutR);
    return datesWithR;
  }

  /**
   * private helper method for determining if the provided LocalDate instance contains
   * the letter 'r' in the full english name of its month
   *
   * @param date LocalDate to check
   * @return boolean if provided LocalDate contains 'r' in month name
   */
  private boolean containsRInMonth(LocalDate date) {
    return date.getMonth().getDisplayName(TextStyle.FULL, Locale.US).contains("r");
  }
}
