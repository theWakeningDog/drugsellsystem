package com.sellsystem.util;

import org.springframework.http.HttpEntity;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhangwei on 2017/4/19.
 */
public class MapUtil {
    /**
     * count : 1
     * infocode : 10000
     * geocodes : [{"formatted_address":"北京市朝阳区阜通东大街|6号楼","city":"北京市","adcode":"110105","level":"门牌号","building":{"name":[],"type":[]},"number":"6号楼","province":"北京市","citycode":"010","street":"阜通东大街","district":"朝阳区","location":"116.484546,39.990064","neighborhood":{"name":[],"type":[]},"township":[]}]
     * status : 1
     * info : OK
     */
    private String count;
    private String infocode;
    private List<GeocodesEntity> geocodes;
    private String status;
    private String info;

    public void setCount(String count) {
        this.count = count;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public void setGeocodes(List<GeocodesEntity> geocodes) {
        this.geocodes = geocodes;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCount() {
        return count;
    }

    public String getInfocode() {
        return infocode;
    }

    public List<GeocodesEntity> getGeocodes() {
        return geocodes;
    }

    public String getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public class GeocodesEntity {
        /**
         * formatted_address : 北京市朝阳区阜通东大街|6号楼
         * city : 北京市
         * adcode : 110105
         * level : 门牌号
         * building : {"name":[],"type":[]}
         * number : 6号楼
         * province : 北京市
         * citycode : 010
         * street : 阜通东大街
         * district : 朝阳区
         * location : 116.484546,39.990064
         * neighborhood : {"name":[],"type":[]}
         * township : []
         */
        private String formatted_address;
        private String city;
        private String adcode;
        private String level;
        private BuildingEntity building;
        private String number;
        private String province;
        private String citycode;
        private String street;
        private String district;
        private String location;
        private NeighborhoodEntity neighborhood;
        private List<?> township;

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setBuilding(BuildingEntity building) {
            this.building = building;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setNeighborhood(NeighborhoodEntity neighborhood) {
            this.neighborhood = neighborhood;
        }

        public void setTownship(List<?> township) {
            this.township = township;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public String getCity() {
            return city;
        }

        public String getAdcode() {
            return adcode;
        }

        public String getLevel() {
            return level;
        }

        public BuildingEntity getBuilding() {
            return building;
        }

        public String getNumber() {
            return number;
        }

        public String getProvince() {
            return province;
        }

        public String getCitycode() {
            return citycode;
        }

        public String getStreet() {
            return street;
        }

        public String getDistrict() {
            return district;
        }

        public String getLocation() {
            return location;
        }

        public NeighborhoodEntity getNeighborhood() {
            return neighborhood;
        }

        public List<?> getTownship() {
            return township;
        }

        public class BuildingEntity {
            /**
             * name : []
             * type : []
             */
            private List<?> name;
            private List<?> type;

            public void setName(List<?> name) {
                this.name = name;
            }

            public void setType(List<?> type) {
                this.type = type;
            }

            public List<?> getName() {
                return name;
            }

            public List<?> getType() {
                return type;
            }
        }

        public class NeighborhoodEntity {
            /**
             * name : []
             * type : []
             */
            private List<?> name;
            private List<?> type;

            public void setName(List<?> name) {
                this.name = name;
            }

            public void setType(List<?> type) {
                this.type = type;
            }

            public List<?> getName() {
                return name;
            }

            public List<?> getType() {
                return type;
            }
        }
    }

    /*public void test() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String address = java.net.URLEncoder.encode("北京市朝阳区阜通东大街6号","utf-8");
        HttpGet httpget = new HttpGet("http://restapi.amap.com/v3/geocode/geo?address="+address+"&output=JSON&key=00f8cac7b5ee6c346091c4ac49189e13");
        CloseableHttpResponse response = httpclient.execute(httpget);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        System.out.println(result);
    }*/
}
