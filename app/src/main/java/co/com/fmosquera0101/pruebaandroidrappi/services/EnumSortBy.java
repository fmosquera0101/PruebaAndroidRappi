package co.com.fmosquera0101.pruebaandroidrappi.services;

public class EnumSortBy {

    enum SortBy {
        RELEASE_DATE_ASCENDING("release_date.asc"),
        RELEASE_DATE_DESCENDING("release_date.desc");
        String value;

        SortBy(String value) {

            this.value = value;
        }


        @Override
        public String toString() {

            return this.value;
        }
    }
}
