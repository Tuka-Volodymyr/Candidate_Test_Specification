package com.example.candidate_test_specification.web.controllers;

import com.example.candidate_test_specification.services.ProductService;
import com.example.candidate_test_specification.web.controllers.product.ProductDto;
import com.example.candidate_test_specification.web.controllers.product.ProductPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testAddProduct_success() throws Exception {
        List<ProductDto> productDtoList=new ArrayList<>();
        ProductDto productDto=ProductDto.builder()
                .entryDate("12-09-1999")
                .itemCode("1111")
                .itemName("hat")
                .itemQuantity(4123)
                .status("Paid")
                .build();
        productDtoList.add(productDto);
        ProductPayload productPayload= ProductPayload.builder()
                .table("products")
                .records(productDtoList)
                .build();

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productPayload)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Product(s) was added."));
    }

    @Test
    public void testAddProduct_fail_empty_status() throws Exception {
        List<ProductDto> productDtoList=new ArrayList<>();
        ProductDto productDto=ProductDto.builder()
                .entryDate("12-09-1999")
                .itemCode("1111")
                .itemName("hat")
                .itemQuantity(4123)
                .status("")
                .build();
        productDtoList.add(productDto);
        ProductPayload productPayload= ProductPayload.builder()
                .table("products")
                .records(productDtoList)
                .build();

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productPayload)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testAddProduct_fail_negative_itemQuantity() throws Exception {
        List<ProductDto> productDtoList=new ArrayList<>();
        ProductDto productDto=ProductDto.builder()
                .entryDate("12-09-1999")
                .itemCode("1111")
                .itemName("hat")
                .itemQuantity(-12)
                .status("")
                .build();
        productDtoList.add(productDto);
        ProductPayload productPayload= ProductPayload.builder()
                .table("products")
                .records(productDtoList)
                .build();

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productPayload)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testAddProduct_fail_empty_table() throws Exception {
        List<ProductDto> productDtoList=new ArrayList<>();
        ProductDto productDto=ProductDto.builder()
                .entryDate("12-09-1999")
                .itemCode("1111")
                .itemName("hat")
                .itemQuantity(4123)
                .status("Paid")
                .build();
        productDtoList.add(productDto);
        ProductPayload productPayload= ProductPayload.builder()
                .table("")
                .records(productDtoList)
                .build();

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productPayload)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testAddProduct_fail_empty_list() throws Exception {
        List<ProductDto> productDtoList=new ArrayList<>();
        ProductPayload productPayload= ProductPayload.builder()
                .table("products")
                .records(productDtoList)
                .build();
        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(productPayload)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    public void testGetAllProducts_success() throws Exception {
        mockMvc.perform(get("/products/all"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}
