package com.example.blog.Model;

import lombok.Data;

@Data
public class KakaoProfile{
    public Long id;
    public String connected_at;
    public Properties properties;
    public kakao_account kakao_account;

    @Data
    public class Properties{
        public String nickname;
        public String thumbnail_image;
        public String profile_image;
    }

    @Data
    public class kakao_account{
        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;

        @Data
        public class Profile{
            public String nickname;
            public String thumbnail_image_url;
            public String profile_image_url;
        }
    }
}