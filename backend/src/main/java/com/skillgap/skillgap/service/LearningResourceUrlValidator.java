
package com.skillgap.skillgap.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

public final class LearningResourceUrlValidator {

    private LearningResourceUrlValidator() {
        // Utility class
    }

    /*
     * Checks whether a learning resource URL
     * has a valid HTTP or HTTPS format.
     */
    public static boolean isValid(String resourceUrl) {

        if (resourceUrl == null || resourceUrl.isBlank()) {
            return false;
        }

        try {
            URI uri = new URI(resourceUrl.trim());

            String scheme = uri.getScheme();

            if (scheme == null) {
                return false;
            }

            String normalizedScheme =
                    scheme.toLowerCase(Locale.ROOT);

            if (!normalizedScheme.equals("http")
                    && !normalizedScheme.equals("https")) {
                return false;
            }

            if (uri.getHost() == null
                    || uri.getHost().isBlank()) {
                return false;
            }

            // Reject embedded username/password.
            if (uri.getRawUserInfo() != null) {
                return false;
            }

            // Reject malformed port numbers.
            if (uri.getRawAuthority() == null
                    || uri.getRawAuthority().isBlank()) {
                return false;
            }

            // Reject control characters.
            for (int i = 0; i < resourceUrl.length(); i++) {

                if (Character.isISOControl(
                        resourceUrl.charAt(i))) {
                    return false;
                }
            }

            return true;

        } catch (URISyntaxException exception) {
            return false;
        }
    }

    /*
     * Validates a URL and returns its
     * trimmed value.
     *
     * Throws HTTP 400 for invalid URLs.
     */
    public static String requireValid(String resourceUrl) {

        if (!isValid(resourceUrl)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Enter a valid HTTP or HTTPS learning resource URL"
            );
        }

        return resourceUrl.trim();
    }
}
