package pl.app.api.interfaces;

import pl.app.api.model.CategoriesModel;
import retrofit2.Call;
import retrofit2.http.GET;
import pl.app.api.model.*;
import retrofit2.http.*;


import java.util.List;

public interface ApiResourceInterface {

    @POST("/user")
    Call<ResponseModel> saveNewUser(
            @Body UserAccountModel userAccountModel
    );

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
            @Path("id") int id,
            @Body CategoriesModel categoriesModel
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

    @PUT("/unit/edit/{id}")
    Call<ResponseModel> editUnitById(
            @Path("id") int id,
            @Body UnitModel unitModel
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

    @GET("/order/all")
    Call<List<OrderModel>> getAllOrders();

    @POST("/order/create")
    Call<ResponseModel> createNewOrder(
            @Body List<OrderProductModel> orderProductList
    );

    @POST("/product")
    Call<ResponseModel> createNewProduct(
            @Body ProductModel productModel
    );

    @GET("/product")
    Call<List<ProductModel>> getAllProducts();

    @GET("/user/all")
    Call<List<UserAccountModel>> getAllUsers();

    @GET("/useraccounttype")
    Call<List<UserAccountTypeModel>> getAllAccountType();

    @GET("/useraccounttype/extra")
    Call<List<UserAccountTypeModel>> getExtraAccountType();

    @PUT("/product/delete/{id}")
    Call<ResponseModel> deleteProductById(
            @Path("id") int id
    );

    @PUT("/product/edit/{id}")
    Call<ResponseModel> editProductById(
            @Path("id") int id,
            @Body ProductModel productModel
    );

    @PUT("/user/edit")
    Call<ResponseModel> editUser(
            @Body UserAccountModel userAccountModel
    );

}
