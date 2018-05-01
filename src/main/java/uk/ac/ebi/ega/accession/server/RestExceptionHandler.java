/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.ac.ebi.ega.accession.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.ValidationException;

@ControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private CollectionValidator collectionValidator;

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiError> handleHttpMediaNotSupported(HttpMediaTypeNotSupportedException ex) {
        return buildResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex, ex.getMessage());
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ApiError> handleValidationException(ValidationException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex, ex.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ex, "Please provide accepted values");
    }

    private ResponseEntity<ApiError> buildResponseEntity(HttpStatus status, Exception ex, String message) {
        return new ResponseEntity<>(new ApiError(status, ex, message), status);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(collectionValidator);
    }
}
