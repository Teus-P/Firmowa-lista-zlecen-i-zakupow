package pl.app.api.interfaces;

import pl.app.api.model.CategoriesModel;
import retrofit2.Call;
import retrofit2.http.GET;
import pl.app.api.model.*;
import retrofit2.http.*;


import java.util.List;

public interface ApiResourceInterface {

    @GET("/categories")
    Call<List<CategoriesModel>> getAllCategories();

    @POST("/categories")
    Call<ResponseModel> createNewCategory(
            @Body CategoriesModel categoriesModel
    );

    @GET("/categories/{name}")
    Call<CategoriesModel> getCategoryByName(
            @Path("name") String categoriesName
    );

    @PUT("/categories/edit/{id}")
    Call<ResponseModel> editCategoryById(
            @Path("id") int id
    );

    @PUT("/categories/delete/{id}")
    Call<ResponseModel> deleteCategoryById(
            @Path("id") int id
    );

    @GET("/unit")
    Call<List<UnitModel>> getAllUnits();

    @POST("/unit")
    Call<ResponseModel> createNewUnit(
            @Body UnitModel unitModel
    );

    @PUT("/unit/edit")
    Call<ResponseModel> editUnitById(
            @Path("id") int id
    );

    @PUT("/unit/delete/{id}")
    Call<ResponseModel> deleteUnitById(
            @Path("id") int id
    );

    @GET("/orderstatus")
    Call<List<OrderStatusModel>> getAllOrderStatus();

    @PUT("/orderstatus/edit/{id}")
    Call<ResponseModel> editOrderStatusById(
            @Path("id") int id
    );

    @POST("/orderstatus")
    Call<ResponseModel> createNewOrderStatus(
            @Body OrderStatusModel orderStatusModel
    );

    @PUT("/orderstatus/delete/{id}")
    Call<ResponseModel> deleteOrderStatus(
            @Path("id") int id
    );

    @GET("/order/{id}")
    Call<OrderModel> getOrderById(
            @Path("id") int id
    );

    @GET("/order/my/all")
    Call<List<OrderModel>> getMyAllOrders();


    @POST("/product")
    Call<ResponseModel> createNewProduct(
            @Body ProductModel productModel
    );


}
