package Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public class TimeZoneEntity {

    // UTC-12:00
    @JsonProperty("Etc/GMT+12")
    public ZonedDateTime etcGmtPlus12;

    // UTC-11:00
    @JsonProperty("Pacific/Pago_Pago")
    public ZonedDateTime pacificPagoPago;

    // UTC-10:00
    @JsonProperty("Pacific/Honolulu")
    public ZonedDateTime pacificHonolulu;

    // UTC-09:00
    @JsonProperty("America/Anchorage")
    public ZonedDateTime americaAnchorage;

    // UTC-08:00
    @JsonProperty("America/Los_Angeles")
    public ZonedDateTime americaLosAngeles;

    // UTC-07:00
    @JsonProperty("America/Denver")
    public ZonedDateTime americaDenver;

    // UTC-06:00
    @JsonProperty("America/Chicago")
    public ZonedDateTime americaChicago;

    // UTC-05:00
    @JsonProperty("America/New_York")
    public ZonedDateTime americaNewYork;

    // UTC-04:00
    @JsonProperty("America/Halifax")
    public ZonedDateTime americaHalifax;

    // UTC-03:30
    @JsonProperty("America/St_Johns")
    public ZonedDateTime americaStJohns;

    // UTC-03:00
    @JsonProperty("America/Argentina/Buenos_Aires")
    public ZonedDateTime americaBuenosAires;

    // UTC-02:00
    @JsonProperty("Atlantic/South_Georgia")
    public ZonedDateTime atlanticSouthGeorgia;

    // UTC-01:00
    @JsonProperty("Atlantic/Azores")
    public ZonedDateTime atlanticAzores;

    // UTC±00:00
    @JsonProperty("Europe/London")
    public ZonedDateTime europeLondon;

    // UTC+01:00
    @JsonProperty("Europe/Paris")
    public ZonedDateTime europeParis;

    // UTC+02:00
    @JsonProperty("Africa/Cairo")
    public ZonedDateTime africaCairo;

    // UTC+03:00
    @JsonProperty("Europe/Moscow")
    public ZonedDateTime europeMoscow;

    // UTC+03:30
    @JsonProperty("Asia/Tehran")
    public ZonedDateTime asiaTehran;

    // UTC+04:00
    @JsonProperty("Asia/Dubai")
    public ZonedDateTime asiaDubai;

    // UTC+04:30
    @JsonProperty("Asia/Kabul")
    public ZonedDateTime asiaKabul;

    // UTC+05:00
    @JsonProperty("Asia/Karachi")
    public ZonedDateTime asiaKarachi;

    // UTC+05:30
    @JsonProperty("Asia/Kolkata")
    public ZonedDateTime asiaKolkata;

    // UTC+05:45
    @JsonProperty("Asia/Kathmandu")
    public ZonedDateTime asiaKathmandu;

    // UTC+06:00
    @JsonProperty("Asia/Dhaka")
    public ZonedDateTime asiaDhaka;

    // UTC+06:30
    @JsonProperty("Asia/Yangon")
    public ZonedDateTime asiaYangon;

    // UTC+07:00
    @JsonProperty("Asia/Bangkok")
    public ZonedDateTime asiaBangkok;

    // UTC+08:00
    @JsonProperty("Asia/Shanghai")
    public ZonedDateTime asiaShanghai;

    // UTC+09:00
    @JsonProperty("Asia/Tokyo")
    public ZonedDateTime asiaTokyo;

    // UTC+09:30
    @JsonProperty("Australia/Adelaide")
    public ZonedDateTime australiaAdelaide;

    // UTC+10:00
    @JsonProperty("Australia/Sydney")
    public ZonedDateTime australiaSydney;

    // UTC+10:30
    @JsonProperty("Australia/Lord_Howe")
    public ZonedDateTime australiaLordHowe;

    // UTC+11:00
    @JsonProperty("Pacific/Noumea")
    public ZonedDateTime pacificNoumea;

    // UTC+12:00
    @JsonProperty("Pacific/Auckland")
    public ZonedDateTime pacificAuckland;

    // UTC+12:45
    @JsonProperty("Pacific/Chatham")
    public ZonedDateTime pacificChatham;

    // UTC+13:00
    @JsonProperty("Pacific/Tongatapu")
    public ZonedDateTime pacificTongatapu;

    // UTC+14:00
    @JsonProperty("Pacific/Kiritimati")
    public ZonedDateTime pacificKiritimati;

    public TimeZoneEntity() {}
}