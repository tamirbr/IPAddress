package com.tamirb.ipaddress.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IpInfo {
    private String city;
    private String country;
    private String countryCode;
    private String isp;
    private Double lat;
    private Double lon;
    private String org;
    private String ip;
    private String region;
    private String regionName;
    private String timeZone;
    private String zip;
}
