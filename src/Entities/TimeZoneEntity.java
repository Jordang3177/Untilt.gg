package Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public class TimeZoneEntity {

    // UTC-12:00
    @JsonProperty("Etc/GMT+12")
    public String etcGmtPlus12;

    // UTC-11:00
    @JsonProperty("Pacific/Pago_Pago")
    public String pacificPagoPago;

    // UTC-10:00
    @JsonProperty("Pacific/Honolulu")
    public String pacificHonolulu;

    // UTC-09:00
    @JsonProperty("America/Anchorage")
    public String americaAnchorage;

    // UTC-08:00
    @JsonProperty("America/Los_Angeles")
    public String americaLosAngeles;

    // UTC-07:00
    @JsonProperty("America/Denver")
    public String americaDenver;

    // UTC-06:00
    @JsonProperty("America/Chicago")
    public String americaChicago;

    // UTC-05:00
    @JsonProperty("America/New_York")
    public String americaNewYork;

    // UTC-04:00
    @JsonProperty("America/Halifax")
    public String americaHalifax;

    // UTC-03:30
    @JsonProperty("America/St_Johns")
    public String americaStJohns;

    // UTC-03:00
    @JsonProperty("America/Argentina/Buenos_Aires")
    public String americaBuenosAires;

    // UTC-02:00
    @JsonProperty("Atlantic/South_Georgia")
    public String atlanticSouthGeorgia;

    // UTC-01:00
    @JsonProperty("Atlantic/Azores")
    public String atlanticAzores;

    // UTC±00:00
    @JsonProperty("Europe/London")
    public String europeLondon;

    // UTC+01:00
    @JsonProperty("Europe/Paris")
    public String europeParis;

    // UTC+02:00
    @JsonProperty("Africa/Cairo")
    public String africaCairo;

    // UTC+03:00
    @JsonProperty("Europe/Moscow")
    public String europeMoscow;

    // UTC+03:30
    @JsonProperty("Asia/Tehran")
    public String asiaTehran;

    // UTC+04:00
    @JsonProperty("Asia/Dubai")
    public String asiaDubai;

    // UTC+04:30
    @JsonProperty("Asia/Kabul")
    public String asiaKabul;

    // UTC+05:00
    @JsonProperty("Asia/Karachi")
    public String asiaKarachi;

    // UTC+05:30
    @JsonProperty("Asia/Kolkata")
    public String asiaKolkata;

    // UTC+05:45
    @JsonProperty("Asia/Kathmandu")
    public String asiaKathmandu;

    // UTC+06:00
    @JsonProperty("Asia/Dhaka")
    public String asiaDhaka;

    // UTC+06:30
    @JsonProperty("Asia/Yangon")
    public String asiaYangon;

    // UTC+07:00
    @JsonProperty("Asia/Bangkok")
    public String asiaBangkok;

    // UTC+08:00
    @JsonProperty("Asia/Shanghai")
    public String asiaShanghai;

    // UTC+09:00
    @JsonProperty("Asia/Tokyo")
    public String asiaTokyo;

    // UTC+09:30
    @JsonProperty("Australia/Adelaide")
    public String australiaAdelaide;

    // UTC+10:00
    @JsonProperty("Australia/Sydney")
    public String australiaSydney;

    // UTC+10:30
    @JsonProperty("Australia/Lord_Howe")
    public String australiaLordHowe;

    // UTC+11:00
    @JsonProperty("Pacific/Noumea")
    public String pacificNoumea;

    // UTC+12:00
    @JsonProperty("Pacific/Auckland")
    public String pacificAuckland;

    // UTC+12:45
    @JsonProperty("Pacific/Chatham")
    public String pacificChatham;

    // UTC+13:00
    @JsonProperty("Pacific/Tongatapu")
    public String pacificTongatapu;

    // UTC+14:00
    @JsonProperty("Pacific/Kiritimati")
    public String pacificKiritimati;

    public TimeZoneEntity() {}
}