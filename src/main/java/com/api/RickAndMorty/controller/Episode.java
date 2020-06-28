package com.api.RickAndMorty.controller;

import com.api.RickAndMorty.exceptions.CustomExceptions;
import com.api.RickAndMorty.services.EpisodeService;
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
@RequestMapping("api/episode")
@Api(value = "Episode Controller", description = "All episode endpoints is under this class.")
public class Episode {

    @Autowired(required = true)
    EpisodeService episodeService;

    /**
     *This method lists all episodes.
     * @return ResponseEntity - all episodes in JSONObject
     */
    @ApiOperation(value = "Episode Class")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 204, message = "The data you were trying to reach is not found")})
    @GetMapping
    public ResponseEntity episode() {
        String data = episodeService.getAllData();
        if (data == null || data.isEmpty()) {
            return new ResponseEntity(new CustomExceptions().RecordNotFoundException(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(data, HttpStatus.OK);
        }

    }

    /**
     * This method returns given episodes ids data.
     *
     * @param id episode id.
     * @return ResponseEntity - episode in JSONObject
     */
    @ApiOperation(value = "findById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@ApiParam(value = "id to find episode object", required = true)
                                   @PathVariable("id") @Validated int id) throws Exception {
        return new ResponseEntity<>(episodeService.findById(String.valueOf(id)), HttpStatus.OK);


    }

    /**
     * This method returns episodes in given page.
     *
     * @param page id.
     * @return ResponseEntity - page episodes in JSONObject
     */
    @ApiOperation(value = "findByPage")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/")
    public ResponseEntity findByPage(@ApiParam(value = "id to find episode object", required = true)
                                     @RequestParam(name = "page") @Validated int page) {
        String data = episodeService.getDataWithPageable(page);
        if (data == null || data.isEmpty()) {
            return new ResponseEntity(new CustomExceptions().RecordNotFoundException(), HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
    }


}
