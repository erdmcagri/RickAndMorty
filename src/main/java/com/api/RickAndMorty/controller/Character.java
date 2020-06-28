package com.api.RickAndMorty.controller;

import com.api.RickAndMorty.exceptions.CustomExceptions;
import com.api.RickAndMorty.services.CharacterService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * JavaDoc
 *
 * @author erdmcagri
 * @version 1.0.0
 * @since 27/06/2020
 */
@RestController
@RequestMapping("api/character")
@Api(value = "Character Controller", description = "All character endpoints is under this class.")
public class Character {

    @Autowired(required = true)
    CharacterService characterService;

    /**
     * This method lists all characters.
     *
     * @return ResponseEntity - all characters in JSONObject
     */
    @ApiOperation(value = "Get method for the list all characters")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 204, message = "The data you were trying to reach is not found")})
    @GetMapping
    public ResponseEntity character() {

        String data = characterService.getAllData();

        if (data == null || data.isEmpty()) {
            return new ResponseEntity(new CustomExceptions().RecordNotFoundException(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(characterService.getAllData(), HttpStatus.OK);
        }
    }

    /**
     * This method returns given character ids data.
     *
     * @param  id characters id
     * @return ResponseEntity - character in JSONObject
     */
    @ApiOperation(value = "findById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The data you were trying to reach is not found")})
    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@ApiParam(value = "id to find character object", required = true)
                                   @PathVariable("id") @Validated int id) throws Exception {

            return new ResponseEntity<>(characterService.findById(String.valueOf(id)), HttpStatus.OK);
    }


    /**
     * This method returns characteres in given page.
     *
     * @param page page id.
     * @return ResponseEntity - page characters in JSONObject
     */
    @ApiOperation(value = "findByPage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/")
    public ResponseEntity findByPage(@ApiParam(value = "id to find character object", required = true)
                                     @RequestParam(name = "page") @Validated int page) {

            String data = characterService.getDataWithPageable(page);
            if (data == null || data.isEmpty()) {
                return new ResponseEntity(new CustomExceptions().RecordNotFoundException(), HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(characterService.getDataWithPageable(page), HttpStatus.OK);
            }
    }

}