
package com.skillgap.skillgap.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class ResumeService {

    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;

    public String extractTextFromPdf(MultipartFile file) {

        // 1. Check whether a file was uploaded
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please upload a resume PDF"
            );
        }

        // 2. Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "PDF file must be smaller than 5 MB"
            );
        }

        // 3. Check uploaded content type
        if (!"application/pdf".equalsIgnoreCase(
                file.getContentType())) {
            throw new IllegalArgumentException(
                    "Only PDF files are allowed"
            );
        }

        try {
            byte[] pdfBytes = file.getBytes();

            // 4. Check actual PDF file signature
            if (pdfBytes.length < 5 ||
                    !"%PDF-".equals(
                            new String(
                                    pdfBytes,
                                    0,
                                    5,
                                    StandardCharsets.US_ASCII
                            ))) {

                throw new IllegalArgumentException(
                        "Invalid PDF file format"
                );
            }

            // 5. Extract readable text
            try (PDDocument document =
                         Loader.loadPDF(pdfBytes)) {

                PDFTextStripper textStripper =
                        new PDFTextStripper();

                String extractedText =
                        textStripper.getText(document);

                // 6. Reject PDFs without readable text
                if (extractedText == null ||
                        extractedText.trim().isEmpty()) {

                    throw new IllegalArgumentException(
                            "No readable text found in the PDF"
                    );
                }

                return extractedText.trim();
            }

        } catch (IOException exception) {
            throw new IllegalArgumentException(
                    "Failed to read resume PDF",
                    exception
            );
        }
    }
}
