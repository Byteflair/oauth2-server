package com.byteflair.oauth.server.springUserDetails;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by rpr on 23/03/16.
 */
public class CustomUserDetailsJwtTokenConverter extends JwtAccessTokenConverter implements TokenEnhancer, AccessTokenConverter {

    private JsonParser objectMapper = JsonParserFactory.create();

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
        LinkedHashMap info = new LinkedHashMap(accessToken.getAdditionalInformation());
        String tokenId = result.getValue();
        if (!info.containsKey("jti")) {
            info.put("jti", tokenId);
        } else {
            tokenId = (String) info.get("jti");
        }

        if (authentication.getPrincipal().getClass() == CustomUserDetails.class) {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            info.put("systemId", customUserDetails.getSystemId());
            info.put("name", customUserDetails.getName());
            info.put("email", customUserDetails.getEmail());
            if (customUserDetails.getPhone() != null) {
                info.put("phone", customUserDetails.getPhone());
            }
            if (customUserDetails.getPhone1() != null) {
                info.put("phone1", customUserDetails.getPhone1());
            }
            if (customUserDetails.getPhone2() != null) {
                info.put("phone2", customUserDetails.getPhone2());
            }
            if (customUserDetails.getPostalAddress() != null) {
                info.put("postalAddress", customUserDetails.getPostalAddress());
            }
            info.put("details", customUserDetails.getDetails());
        }

        result.setAdditionalInformation(info);
        result.setValue(this.encode(result, authentication));
        OAuth2RefreshToken refreshToken = result.getRefreshToken();
        if (refreshToken != null) {
            DefaultOAuth2AccessToken encodedRefreshToken = new DefaultOAuth2AccessToken(accessToken);
            encodedRefreshToken.setValue(refreshToken.getValue());

            try {
                Map refreshTokenInfo = this.objectMapper
                    .parseMap(JwtHelper.decode(refreshToken.getValue()).getClaims());
                if (refreshTokenInfo.containsKey("jti")) {
                    encodedRefreshToken.setValue(refreshTokenInfo.get("jti").toString());
                }
            } catch (IllegalArgumentException var11) {
            }

            LinkedHashMap refreshTokenInfo1 = new LinkedHashMap(accessToken.getAdditionalInformation());
            refreshTokenInfo1.put("jti", encodedRefreshToken.getValue());
            refreshTokenInfo1.put("ati", tokenId);
            encodedRefreshToken.setAdditionalInformation(refreshTokenInfo1);
            Object token = new DefaultOAuth2RefreshToken(this.encode(encodedRefreshToken, authentication));
            if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
                Date expiration = ((ExpiringOAuth2RefreshToken) refreshToken).getExpiration();
                encodedRefreshToken.setExpiration(expiration);
                token = new DefaultExpiringOAuth2RefreshToken(this.encode(encodedRefreshToken, authentication),
                                                              expiration);
            }

            result.setRefreshToken((OAuth2RefreshToken) token);
        }

        return result;
    }
}
