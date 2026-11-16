package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Booking {

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("totalprice")
    private Integer totalprice;

    @JsonProperty("depositpaid")
    private Boolean depositpaid;

    @JsonProperty("bookingdates")
    private BookingDates bookingdates;

    @JsonProperty("additionalneeds")
    private String additionalneeds;

    public Booking() {
    }

    public Booking(String firstname, String lastname, Integer totalprice, Boolean depositpaid,
                   BookingDates bookingdates, String additionalneeds) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalprice = totalprice;
        this.depositpaid = depositpaid;
        this.bookingdates = bookingdates;
        this.additionalneeds = additionalneeds;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    public Boolean getDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(Boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(firstname, booking.firstname) &&
                Objects.equals(lastname, booking.lastname) &&
                Objects.equals(totalprice, booking.totalprice) &&
                Objects.equals(depositpaid, booking.depositpaid) &&
                Objects.equals(bookingdates, booking.bookingdates) &&
                Objects.equals(additionalneeds, booking.additionalneeds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", totalprice=" + totalprice +
                ", depositpaid=" + depositpaid +
                ", bookingdates=" + bookingdates +
                ", additionalneeds='" + additionalneeds + '\'' +
                '}';
    }

    // Static nested class for Booking Dates
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BookingDates {

        @JsonProperty("checkin")
        private String checkin;

        @JsonProperty("checkout")
        private String checkout;

        public BookingDates() {
        }

        public BookingDates(String checkin, String checkout) {
            this.checkin = checkin;
            this.checkout = checkout;
        }

        public String getCheckin() {
            return checkin;
        }

        public void setCheckin(String checkin) {
            this.checkin = checkin;
        }

        public String getCheckout() {
            return checkout;
        }

        public void setCheckout(String checkout) {
            this.checkout = checkout;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BookingDates that = (BookingDates) o;
            return Objects.equals(checkin, that.checkin) && Objects.equals(checkout, that.checkout);
        }

        @Override
        public int hashCode() {
            return Objects.hash(checkin, checkout);
        }

        @Override
        public String toString() {
            return "BookingDates{" +
                    "checkin='" + checkin + '\'' +
                    ", checkout='" + checkout + '\'' +
                    '}';
        }
    }

    // Static nested class for Create Booking Response
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BookingResponse {

        @JsonProperty("bookingid")
        private Integer bookingid;

        @JsonProperty("booking")
        private Booking booking;

        public BookingResponse() {
        }

        public BookingResponse(Integer bookingid, Booking booking) {
            this.bookingid = bookingid;
            this.booking = booking;
        }

        public Integer getBookingid() {
            return bookingid;
        }

        public void setBookingid(Integer bookingid) {
            this.bookingid = bookingid;
        }

        public Booking getBooking() {
            return booking;
        }

        public void setBooking(Booking booking) {
            this.booking = booking;
        }

        @Override
        public String toString() {
            return "BookingResponse{" +
                    "bookingid=" + bookingid +
                    ", booking=" + booking +
                    '}';
        }
    }

    // Static nested class for Booking ID item
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BookingId {

        @JsonProperty("bookingid")
        private Integer bookingid;

        public BookingId() {
        }

        public BookingId(Integer bookingid) {
            this.bookingid = bookingid;
        }

        public Integer getBookingid() {
            return bookingid;
        }

        public void setBookingid(Integer bookingid) {
            this.bookingid = bookingid;
        }

        @Override
        public String toString() {
            return "BookingId{" +
                    "bookingid=" + bookingid +
                    '}';
        }
    }

    // Static nested class for Auth request
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AuthCredentials {

        @JsonProperty("username")
        private String username;

        @JsonProperty("password")
        private String password;

        public AuthCredentials() {
        }

        public AuthCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Static nested class for Auth response
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TokenResponse {

        @JsonProperty("token")
        private String token;

        @JsonProperty("reason")
        private String reason;

        public TokenResponse() {
        }

        public TokenResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        @Override
        public String toString() {
            return "TokenResponse{" +
                    "token='" + token + '\'' +
                    ", reason='" + reason + '\'' +
                    '}';
        }
    }
}
