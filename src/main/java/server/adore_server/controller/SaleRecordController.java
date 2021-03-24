package server.adore_server.controller;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import server.adore_server.model.SaleRecord;

import server.adore_server.model.SaleRecordDeleted;
import server.adore_server.repository.StockTableRepository;
import server.adore_server.request.*;
import server.adore_server.response.ReturnedResponse2;
import server.adore_server.response.ReturnResponse;
import server.adore_server.service.SaleRecordService;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.validation.Valid;
import java.io.DataInput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")

public class SaleRecordController {

    @Autowired
    SaleRecordService saleRecordService;

    @Autowired
    StockTableRepository stockTableRepository;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/add")
    public void addSaleRecord(@RequestBody String jsonObject) throws Exception {
        JSONObject jsonObject1 = new JSONObject(jsonObject);

        var jsonA = jsonObject1.getJSONArray("saleRecord");
        String json = jsonA.toString();
        long card_id = jsonObject1.getLong("card_id");
        ObjectMapper mapper = new ObjectMapper();
        List<SaleRecord> saleRecord = Arrays.asList(mapper.readValue(json, SaleRecord[].class));


        System.out.println(saleRecord);

        int change = saleRecord.get(0).getChange_();
        saleRecordService.addSaleRecord(saleRecord, card_id);

        for (SaleRecord l : saleRecord) {
            if (saleRecordService.checkExtendLive(l.getName(), l.getOptions()) == false) {
                if (change == 1) {
                    saleRecordService.setProductActive0(l.getProductId());
                }
            }
        }
    }

    @GetMapping(value = "/getTr")
    public long getMaxTransactionId() {
        try {
            return saleRecordService.getMaxTrId();
        } catch (Exception e) {
            return 0;
        }
    }

    @GetMapping(value = "/getLastSaleRecord", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getLastSaleRecord() {
        try {
            return ResponseEntity.ok(saleRecordService.getLastSaleRecord());

        } catch (Exception e) {

            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    @Modifying
    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/deleteByTransactionId")
    public int deleteByTransactionId(@RequestBody OneStringRequest oneStringRequest) {
        return saleRecordService.deleteByTransaction(oneStringRequest.getS());
    }

    @PostMapping(consumes = MediaType.ALL_VALUE,
            value = "/addCommentToLastSaleRecord")
    public void addCommentToLAstSaleRecord(@RequestBody OneStringRequest oneStringRequest) {
        saleRecordService.editComment(oneStringRequest.getS());
    }

    @PostMapping(consumes = MediaType.ALL_VALUE, value = "/isExistBaselinkerId")
    public int isExistBaselinkerId(@RequestBody IdRequest idRequest) {
        if (saleRecordService.baselinkerIdExist(idRequest.getId()).size() > 0)
            return 1;
        else
            return 0;
    }

    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getAllByEan")
    public List<ReturnResponse> getAllByEan(@RequestBody EanRequest eanRequest) {
        return saleRecordService.getAllByEan(eanRequest.getEan());
    }

    @PostMapping(consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/getAllByTransaction_id")
    public List<SaleRecord> getAllByTransaction_id(@RequestBody IdRequest idRequest) {
        return saleRecordService.getAllByTransaction_id((int) idRequest.getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, value = "/changeSaleRecord")
    public boolean changeSaleRecord(@RequestBody String s) {
        JSONObject jsonObject = new JSONObject(s);
        return saleRecordService.changeSaleRecord(jsonObject);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/returnedNameAndOptions")
    public ResponseEntity<?> getNameAndOptions(@Valid @RequestBody NameAndOptionsRequest nameAndOptionsRequestRequest) {
        List<ReturnedResponse2> returnedResponse2 = saleRecordService.getAllByNameAndOptions(nameAndOptionsRequestRequest.getName(), nameAndOptionsRequestRequest.getOptions());
        return ResponseEntity.ok(returnedResponse2);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, value = "/returnedName")
    public ResponseEntity<?> getName(@Valid @RequestBody NameRequest nameRequest) {
        List<ReturnedResponse2> returnedResponse2 = saleRecordService.getAllByName(nameRequest.getName());
        return ResponseEntity.ok(returnedResponse2);
    }
}


