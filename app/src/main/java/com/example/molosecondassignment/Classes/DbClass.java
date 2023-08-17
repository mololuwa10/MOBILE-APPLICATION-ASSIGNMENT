package com.example.molosecondassignment.Classes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.example.molosecondassignment.UserDashboard;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class DbClass extends SQLiteOpenHelper {
    // DATABASE
    private static String DBNAME = "AS2.db";
    private static int DB_VERSION = 2;
    // ----------------------------------------------------

    // USERS DATABASE TABLE COLUMNS
    private static String tblUsers = "tblUsers";
    private static String UserID = "UserID";
    private static String UserFullName = "UserFullName";
    private static String UserPassword = "UserPassword";
    private static String UserEmail = "UserEmail";
    private static String DateRegistered = "DateRegistered";
    private static String DateUpdated = "DateUpdated";
    private static String UserAddress = "UserAddress";
    private static String UserPostcode = "UserPostcode";
    private static String UserHobbies = "UserHobbies";
    private static String UserType = "UserType";
    private static String UserImage = "UserImage";
    private static String UserNumber = "UserNumber";

    // ---------------------------------------------------

    // PRODUCTS DATABASE TABLE COLUMNS
    private static String tblProducts = "tblProducts";
    private static String ProductId = "ProductId";
    private static String ProductName = "ProductName";
    private static String ProductDescription = "ProductDescription";
    private static String ProductPrice = "ProductPrice";
    private static String ProductListPrice = "ProductListPrice";
    private static String ProductRetailPrice = "ProductRetailPrice";
    private static String DateCreated = "DateCreated";
    private static String ProductImage = "ProductImage";
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] imageInBytes;
    // -------------------------------------------------------------------

    // CATEGORIES TABLES WITH COLUMNS
    private static String tblCategories = "tblCategories";
    private static String CategoryId = "CategoryId";
    private static String CategoryName = "CategoryName";
    private static String CategoryImage = "CategoryImage";
    // -----------------------------------------------

    // ORDERS TABLE WITH COLUMNS
    private static String tblOrders = "tblOrders";
    private static String Order_id = "Order_id";
    private static String product_id = "product_id";
    private static String user_id = "user_id";
    private static String total_amount = "total_amount";
    private static String order_date = "order_date";
    private static String status = "status";
    private static String shipping_address = "shipping_address";

    // ----------------------------------------------
    // BASKET TABLE WITH COLUMNS
    private static String tblBasket = "tblBasket";
    private static String BasketID = "BasketID";
    private static String BasketQuantity = "BasketQuantity";
    private static String BasketPrice = "BasketPrice";
    private static String TotalPrice = "TotalPrice";
    private static String BasketStatus = "BasketStatus";
    // ---------------------------------------
    //

    private static String tblOrderProducts = "tblOrderProducts";
    private static String orderProductId = "orderProductId";


    public DbClass(@Nullable Context context) {
        super(context, DBNAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String usersQuery = "CREATE TABLE " + tblUsers + "("
                +UserID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +UserFullName+" TEXT, "
                +UserPassword+" TEXT, "
                +UserEmail+" TEXT, "
                +DateRegistered+" TEXT, "+
                DateUpdated+" TEXT, "+
                UserAddress+" TEXT, "+
                UserPostcode+" TEXT, "+
                UserHobbies+" TEXT, "+
                UserImage+" TEXT, "+
                UserNumber+" TEXT, "+
                UserType+" INTEGER)";

        String productsQuery = "CREATE TABLE " + tblProducts + "("
                +ProductId+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +ProductName+" TEXT, "
                +ProductDescription+" TEXT, "
                +ProductPrice+" TEXT, "
                +ProductListPrice+" TEXT, "
                +ProductRetailPrice+" TEXT, "
                +DateCreated+" TEXT, "
                +DateUpdated+" TEXT, "
                +ProductImage+" TEXT, "
                +"CategoryID INTEGER REFERENCES "+tblCategories+"("+CategoryId+"))";

        String categoriesQuery = "CREATE TABLE " + tblCategories + " ("
                +CategoryId+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +CategoryImage+" TEXT, "
                +CategoryName+" TEXT)";

        String ordersQuery = "CREATE TABLE " + tblOrders + " ("
                +Order_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +total_amount+" TEXT, "
                +order_date+" TEXT, "
                +status+" TEXT, "
                +shipping_address+" TEXT, "
                +user_id+ " INTEGER REFERENCES "+tblUsers+"("+UserID+"), "
                +product_id+ " INTEGER REFERENCES "+tblProducts+"("+ProductId+"))";

        String basketQuery = "CREATE TABLE " + tblBasket + " ("
                +BasketID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +BasketQuantity+" TEXT, "
                +BasketPrice+" TEXT, "
                +TotalPrice+" TEXT, "
                +BasketStatus+" TEXT, "
                +UserID+" INTEGER REFERENCES "+tblUsers+"("+UserID+"), "
                +ProductId+" INTEGER REFERENCES "+tblProducts+"("+ProductId+"))";

        String orderProductsQuery = "CREATE TABLE " + tblOrderProducts + " ("
                +orderProductId+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Order_id+" INTEGER REFERENCES "+tblOrders+"("+Order_id+"), "
                +ProductId+" INTEGER REFERENCES "+tblProducts+"("+ProductId+"))";

        db.execSQL(productsQuery);
        db.execSQL(basketQuery);
        db.execSQL(usersQuery);
        db.execSQL(categoriesQuery);
        db.execSQL(ordersQuery);
        db.execSQL(orderProductsQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String userQuery = "DROP TABLE IF EXISTS " + tblUsers;
        String productsQuery = "DROP TABLE IF EXISTS " + tblProducts;
        String categoriesQuery = "DROP TABLE IF EXISTS " + tblCategories;
        String ordersQuery = "DROP TABLE IF EXISTS " + tblOrders;
        String basketQuery = "DROP TABLE IF EXISTS " + tblBasket;
        String orderProductQuery = "DROP TABLE IF EXISTS " + tblOrderProducts;

        db.execSQL(basketQuery);
        db.execSQL(userQuery);
        db.execSQL(productsQuery);
        db.execSQL(categoriesQuery);
        db.execSQL(ordersQuery);
        db.execSQL(orderProductQuery);
        onCreate(db);
        db.close();
    }

    // METHOD FOR ADDING THE USER
    public boolean addUsers(Users users) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserFullName, users.getFullName());
        values.put(UserPassword, users.getPassword());
        values.put(UserEmail, users.getEmail());
        values.put(UserHobbies, users.getHobbies());
        values.put(DateRegistered, users.getDateRegistered());
        values.put(DateUpdated, users.getDateUpdated());
        values.put(UserAddress, users.getAddress());
        values.put(UserPostcode, users.getPostCode());
        values.put(UserType, 0);

       long l = db.insert(tblUsers, null, values);
        db.close();

        if (l>0){
            return true;
        } else {
            return false;
        }
    }

    // METHOD FOR UPDATING THE USER
    public boolean updateUsers(Users users){
        SQLiteDatabase dbEvents = this.getWritableDatabase();
        ContentValues userValues = new ContentValues();

        Bitmap imageToStoreBitmap = users.getUserImage();

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        userValues.put(UserFullName, users.getFullName());
        userValues.put(UserPassword, users.getPassword());
        userValues.put(UserEmail, users.getEmail());
        userValues.put(UserHobbies, users.getHobbies());
        userValues.put(DateRegistered, users.getDateRegistered());
        userValues.put(DateUpdated, users.getDateUpdated());
        userValues.put(UserAddress, users.getAddress());
        userValues.put(UserPostcode, users.getPostCode());
        userValues.put(UserImage, imageInBytes);
        userValues.put(UserNumber, users.getUserNumber());
        userValues.put(UserType, users.getUserType());

        int endResult = dbEvents.update(tblUsers, userValues, UserID + "=?", new String[]{String.valueOf(users.getId())});
        return endResult > 0;
    }

    // METHOD FOR DELETING THE USER
    public boolean deleteUsers(Users users) {
        SQLiteDatabase dbUser = this.getWritableDatabase();
        int endResult = dbUser.delete(tblUsers, UserID + "=?", new String[]{String.valueOf(users.getId())});
        return endResult > 0;
    }

    // READ ALL USERS
    public ArrayList<Users> ReadAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblUsers;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Users> userItem = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow(UserID));
            String strUserName = cursor.getString(cursor.getColumnIndexOrThrow(UserFullName));
            String strUserEmail = cursor.getString(cursor.getColumnIndexOrThrow(UserEmail));
            String strUserPassword = cursor.getString(cursor.getColumnIndexOrThrow(UserPassword));
            String strUserHobbies = cursor.getString(cursor.getColumnIndexOrThrow(UserHobbies));
            String strUserAddress = cursor.getString(cursor.getColumnIndexOrThrow(UserAddress));
            String dateRegistered = cursor.getString(cursor.getColumnIndexOrThrow(DateRegistered));
            String dateUpdated = cursor.getString(cursor.getColumnIndexOrThrow(DateUpdated));
            String strUserPostcode = cursor.getString(cursor.getColumnIndexOrThrow(UserPostcode));
            String userType = cursor.getString(cursor.getColumnIndexOrThrow(UserType));
            String userNumber = cursor.getString(cursor.getColumnIndexOrThrow(UserNumber));
            byte[] userImageInBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(UserImage));

            Bitmap userImage = null;
            if (userImageInBytes != null && userImageInBytes.length > 0) {
                userImage = BitmapFactory.decodeByteArray(userImageInBytes, 0, userImageInBytes.length);
            }

            Users u1 = new Users(id, strUserName, strUserEmail, dateRegistered, dateUpdated, strUserPassword, strUserHobbies, strUserPostcode, strUserAddress, userType, userNumber, userImage);
            userItem.add(u1);
            cursor.moveToNext();
        }

        cursor.close();
        return userItem;
    }

    // METHOD FOR ADDING CATEGORIES
    public boolean addCategories(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Bitmap imageToStoreBitmap = category.getCategoryImage();

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        values.put(CategoryName, category.getCategoryName());
        values.put(CategoryImage, imageInBytes);

        long l = db.insert(tblCategories, null, values);
        db.close();

        return l > 0;
    }

    // METHOD FOR DELETING THE CATEGORIES
    public boolean deleteCategories(Category category) {
        SQLiteDatabase dbCategories = this.getWritableDatabase();
        int endResult = dbCategories.delete(tblCategories, CategoryId + "=?", new String[]{String.valueOf(category.getId())});

        return endResult > 0;
    }

    // METHOD FOR UPDATING THE CATEGORIES
    public boolean updateCategories(Category category) {
        SQLiteDatabase dbCategories = this.getWritableDatabase();

        Bitmap imageToStoreBitmap = category.getCategoryImage();

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues categoriesValues = new ContentValues();
        categoriesValues.put(CategoryName, category.getCategoryName());
        categoriesValues.put(CategoryImage, imageInBytes);

        int endResult = dbCategories.update(tblCategories, categoriesValues, CategoryId + "=?", new String[]{String.valueOf(category.getId())});
        return endResult > 0;
    }

    // METHOD TO READ ALL CATEGORIES
    public ArrayList<Category> ReadAllCategories() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblCategories;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Category> categoriesItem = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(CategoryId));
            String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(CategoryName));

            byte[] categoryImageInBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(CategoryImage));
            Bitmap categoryImage = null;
            if (categoryImageInBytes != null && categoryImageInBytes.length > 0) {
                categoryImage = BitmapFactory.decodeByteArray(categoryImageInBytes, 0, categoryImageInBytes.length);
            }
            Category c1 = new Category(categoryName, categoryImage, categoryId);
            categoriesItem.add(c1);
            cursor.moveToNext();
        }

        cursor.close();
        return categoriesItem;
    }

    //METHOD TO ADD PRODUCTS
    public boolean addProducts(Products products) {
        SQLiteDatabase db = this.getWritableDatabase();

        Bitmap imageToStoreBitmap = products.getProductImage();

        byteArrayOutputStream = new ByteArrayOutputStream();
        imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        imageInBytes = byteArrayOutputStream.toByteArray();

        ContentValues productValues = new ContentValues();
        productValues.put(ProductName, products.getProductName());
        productValues.put(ProductDescription, products.getProductDescription());
        productValues.put(ProductPrice, products.getProductPrice());
        productValues.put(ProductListPrice, products.getProductListPrice());
        productValues.put(ProductRetailPrice, products.getProductRetailPrice());
        productValues.put(DateCreated, products.getDateCreated());
        productValues.put(DateUpdated, products.getDateUpdated());
        productValues.put(ProductImage, imageInBytes);
        productValues.put("CategoryID", products.getCategoryId());

        long l = db.insert(tblProducts, null, productValues);
        db.close();

        return l > 0;
    }

    // METHOD FOR UPDATING THE PRODUCTS
    public boolean updateProducts(Products products) {
        SQLiteDatabase db = this.getWritableDatabase();
        Bitmap imageToStoreBitmap = products.getProductImage();

        if (imageToStoreBitmap != null) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            imageInBytes = byteArrayOutputStream.toByteArray();

            ContentValues productValues = new ContentValues();
            productValues.put(ProductName, products.getProductName());
            productValues.put(ProductDescription, products.getProductDescription());
            productValues.put(ProductPrice, products.getProductPrice());
            productValues.put(ProductListPrice, products.getProductListPrice());
            productValues.put(ProductRetailPrice, products.getProductRetailPrice());
            productValues.put(DateCreated, products.getDateCreated());
            productValues.put(DateUpdated, products.getDateUpdated());
            productValues.put(ProductImage, imageInBytes);
            productValues.put("CategoryID", products.getCategoryId());

            int endResult = db.update(tblProducts, productValues, ProductId + "=?", new String[]{String.valueOf(products.getId())});
            return endResult > 0;
        } else {
            return false;
        }
    }

    // METHOD TO DELETE PRODUCTS
    public boolean deleteProducts(Products products) {
        SQLiteDatabase dbProducts = this.getWritableDatabase();
        int endResult = dbProducts.delete(tblProducts, ProductId + "=?", new String[]{String.valueOf(products.getId())});
        if (endResult > 0) {
            return true;
        } else {
            return false;
        }
    }

   // METHOD TO READ ALL PRODUCTS
    public ArrayList<Products> ReadAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblProducts;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Products> productsItem = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int productId = cursor.getInt(cursor.getColumnIndexOrThrow(ProductId));
            String productName = cursor.getString(cursor.getColumnIndexOrThrow(ProductName));
            String productDescription = cursor.getString(cursor.getColumnIndexOrThrow(ProductDescription));
            String productPrice = cursor.getString(cursor.getColumnIndexOrThrow(ProductPrice));
            String productListPrice = cursor.getString(cursor.getColumnIndexOrThrow(ProductListPrice));
            String productRetailPrice = cursor.getString(cursor.getColumnIndexOrThrow(ProductRetailPrice));
            String dateCreated = cursor.getString(cursor.getColumnIndexOrThrow(DateCreated));
            String dateUpdated = cursor.getString(cursor.getColumnIndexOrThrow(DateUpdated));
            int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("CategoryID"));
            byte[] productImageInBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(ProductImage));

            Bitmap productImage = BitmapFactory.decodeByteArray(productImageInBytes, 0, productImageInBytes.length);

            Products p1 = new Products(productId, productName, productDescription, productPrice, productListPrice, productRetailPrice, dateCreated, dateUpdated, categoryId, productImage);
            productsItem.add(p1);
            cursor.moveToNext();
        }

        cursor.close();
        return productsItem;
    }

    // METHOD TO ADD ORDERS
    public boolean addOrders(Orders orders) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues orderValues = new ContentValues();
        orderValues.put(total_amount, orders.getTotalAmount());
        orderValues.put(order_date, orders.getDateOrdered());
        orderValues.put(status, orders.getOrderStatus());
        orderValues.put(shipping_address, orders.getShippingAddress());
        orderValues.put(user_id, orders.getUserId());

        ArrayList<Products> productList = orders.getProducts();
        StringBuilder productIds = new StringBuilder();
        for (int i = 0; i < productList.size(); i++) {
            Products product = productList.get(i);
            int productId = product.getId();

            //appending the product id to the string builder
            if(i > 0) {
                productIds.append(",");
            }
            productIds.append(productId);
        }

        // Store the comma separated Product Ids in the table
        orderValues.put(product_id, productIds.toString());

        long l = db.insert(tblOrders, null, orderValues);
        db.close();

        return l > 0;
    }

    // METHOD FOR DELETING ORDERS
    public boolean deleteOrders(Orders orders) {
        SQLiteDatabase dbOrders = this.getWritableDatabase();
        int endResult = dbOrders.delete(tblOrders, Order_id + "=?", new String[]{String.valueOf(orders.getOrderId())});
        return endResult > 0;
    }

    // METHOD FOR READING ALL ORDERS
    public ArrayList<Orders> getAllOrders() {
        ArrayList<Orders> ordersList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblOrders;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(Order_id));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(user_id));
                String totalAmount = cursor.getString(cursor.getColumnIndexOrThrow(total_amount));
                String dateOrdered = cursor.getString(cursor.getColumnIndexOrThrow(order_date));
                String orderStatus = cursor.getString(cursor.getColumnIndexOrThrow(status));
                String shippingAddress = cursor.getString(cursor.getColumnIndexOrThrow(shipping_address));
                String productIds = cursor.getString(cursor.getColumnIndexOrThrow(product_id));

                // Split the comma-separated product IDs into an array of integers
                String[] productIdArray = productIds.split(",");
                ArrayList<Products> productList = new ArrayList<>();
                for (String productIdStr : productIdArray) {
                    int productId = Integer.parseInt(productIdStr);
                    Products product = getProductById(productId);
                    if (product != null) {
                        productList.add(product);
                    }
                }

                // Create Orders object
                Orders order = new Orders(userId, productList, totalAmount, dateOrdered, orderStatus, shippingAddress);
                order.setOrderId(orderId);

                ordersList.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return ordersList;
    }

    public ArrayList<Orders> getOrdersByUserId(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Orders> ordersList = new ArrayList<>();

        String query = "SELECT * FROM " + tblOrders + " WHERE " + user_id + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(Order_id));
                String orderStatus = cursor.getString(cursor.getColumnIndexOrThrow(status));

                // Retrieve the products associated with the order
                ArrayList<Products> productsList = getProductsByOrderId(orderId);

                Orders order = new Orders(orderId, productsList, orderStatus);
                ordersList.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ordersList;
    }

    private ArrayList<Products> getProductsByOrderId(int orderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Products> productsList = new ArrayList<>();

        String query = "SELECT * FROM " + tblOrders + " WHERE " + Order_id + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});

        if (cursor.moveToFirst()) {
            String productIds = cursor.getString(cursor.getColumnIndexOrThrow(product_id));
            String[] productIdArray = productIds.split(",");

            for (String productId : productIdArray) {
                int id = Integer.parseInt(productId);
                Products product = getProductById(id);
                if (product != null) {
                    productsList.add(product);
                }
            }
        }
        cursor.close();
        db.close();

        return productsList;
    }

//    public boolean addOrderProducts(ArrayList<OrdersProduct> ordersProducts) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        long result = -1;
//
//        for (OrdersProduct ordersProduct : ordersProducts) {
//            int orderId = ordersProduct.getOrderId();
//            ArrayList<Products> productList = ordersProduct.getProducts();
//
//            for (Products product : productList) {
//                int productId = product.getId();
//
//                ContentValues orderProductsValues = new ContentValues();
//                orderProductsValues.put(Order_id, orderId);
//                orderProductsValues.put(ProductId, productId);
//
//                long rowId = db.insert(tblOrderProducts, null, orderProductsValues);
//                if (rowId != -1) {
//                    result = rowId;
//                }
//            }
//        }
//
//        db.close();
//
//        return result != -1;
//    }

    public boolean addOrderProducts(int orderId, ArrayList<Products> products) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = -1;

        for (Products product : products) {
            int productId = product.getId();

            ContentValues orderProductsValues = new ContentValues();
            orderProductsValues.put(Order_id, orderId);
            orderProductsValues.put(ProductId, productId);

            long rowId = db.insert(tblOrderProducts, null, orderProductsValues);
            if (rowId != -1) {
                result = rowId;
            }
        }

        db.close();

        return result != -1;
    }

    public String getUserFullName(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String fullName = "";

        String query = "SELECT " + UserFullName + " FROM " + tblUsers + " WHERE " + UserID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            fullName = cursor.getString(cursor.getColumnIndexOrThrow(UserFullName));
        }

        cursor.close();
        db.close();

        return fullName;
    }


//    public boolean insertOtherProducts(int orderId, int productId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Order_id, orderId);
//        values.put(product_id, productId);
//        long result = db.insert(tblOrderProducts, null, values);
//
//        db.close();
//        return result != -1;
//    }

    public int getGeneratedOrderId() {
        SQLiteDatabase db = this.getReadableDatabase();
        int orderId = -1;

        String query = "SELECT last_insert_rowid() AS " + Order_id;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            orderId = cursor.getInt(cursor.getColumnIndexOrThrow(Order_id));
        }

        cursor.close();
        db.close();

        return orderId;
    }

        public boolean addBaskets(Baskets baskets) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues basketValues = new ContentValues();
        basketValues.put(BasketQuantity, baskets.getBasketQuantity());
        basketValues.put(BasketPrice, baskets.getBasketPrice());
        basketValues.put(TotalPrice, baskets.getTotalPrice());
        basketValues.put(BasketStatus, baskets.getBasketStatus());
        basketValues.put(UserID, baskets.getUserId());
        basketValues.put(ProductId, baskets.getProductId());

        long l = db.insert(tblBasket, null, basketValues);
        db.close();

        return l > 0;
    }

    public ArrayList<Baskets> ReadAllBaskets() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblBasket;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Baskets> basketsItem = new ArrayList<>();
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            int basketId = cursor.getInt(cursor.getColumnIndexOrThrow(BasketID));
            String basketQuantity = cursor.getString(cursor.getColumnIndexOrThrow(BasketQuantity));
            String basketPrice = cursor.getString(cursor.getColumnIndexOrThrow(BasketPrice));
            String totalPrice = cursor.getString(cursor.getColumnIndexOrThrow(TotalPrice));
            String basketStatus = cursor.getString(cursor.getColumnIndexOrThrow(BasketStatus));
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(UserID));
            int productId = cursor.getInt(cursor.getColumnIndexOrThrow(ProductId));

            Baskets baskets = new Baskets(basketId, userId, productId, basketQuantity, basketPrice, totalPrice, basketStatus);
            basketsItem.add(baskets);
            cursor.moveToNext();
        }

        cursor.close();
        return basketsItem;
    }

    // METHOD TO DELETE PRODUCTS
    public boolean deleteProductsFromCart(Baskets baskets) {
        SQLiteDatabase dbProducts = this.getWritableDatabase();
        int endResult = dbProducts.delete(tblBasket, BasketID + "=?", new String[]{String.valueOf(baskets.getBasketId())});
        return endResult > 0;
    }

    public boolean deleteProductFromBasket(int userId, int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = UserID + " = ? AND " + ProductId + " = ?";
        String[] whereArgs = {String.valueOf(userId), String.valueOf(productId)};
        int deletedRows = db.delete(tblBasket, whereClause, whereArgs);
        db.close();
        return deletedRows > 0;
    }

    @SuppressLint("Range")
    public Products getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Products product = null;

        String[] columns = {ProductId, ProductName, ProductPrice, ProductImage};
        String selection = ProductId + " = ?";
        String[] selectionArgs = {String.valueOf(productId)};

        Cursor cursor = db.query(tblProducts, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            String productName = cursor.getString(cursor.getColumnIndex(ProductName));
            String productPrice = cursor.getString(cursor.getColumnIndex(ProductPrice));
            byte[] productImageBytes = cursor.getBlob(cursor.getColumnIndex(ProductImage));
            Bitmap productImage = BitmapFactory.decodeByteArray(productImageBytes, 0, productImageBytes.length);

            product = new Products(productName, productPrice, productImage);
            product.setId(productId);
        }

        cursor.close();
        db.close();
        return product;
    }

    public void updateBasketQuantity(Baskets basket) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues basketValues = new ContentValues();
        basketValues.put(BasketQuantity, basket.getBasketQuantity());

        // Updating basket quantity row
        db.update(tblBasket, basketValues, BasketID + "=?", new String[]{String.valueOf(basket.getBasketId())});
        db.close();
    }

    public void updateBasketPrice(Baskets basket) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues basketValues = new ContentValues();
        basketValues.put(BasketPrice, basket.getBasketPrice());

        // Updating basket price row
        db.update(tblBasket, basketValues , BasketID + "=?", new String[]{String.valueOf(basket.getBasketId())});
        db.close();
    }

    public ArrayList<Baskets> readBasketsByUserId(int userId) {
        ArrayList<Baskets> baskets = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblBasket + " WHERE " + UserID + " = " + userId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String basketId = cursor.getString(cursor.getColumnIndexOrThrow(BasketID));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow(ProductId));
                String basketQuantity = cursor.getString(cursor.getColumnIndexOrThrow(BasketQuantity));
                int UserId = cursor.getInt(cursor.getColumnIndexOrThrow(UserID));
                String basketPrice = cursor.getString(cursor.getColumnIndexOrThrow(BasketPrice));

                Baskets basket = new Baskets(UserId, productId, basketQuantity, basketId, basketPrice);
                baskets.add(basket);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return baskets;
    }

    // METHOD TO READ THE BASKETS TABLE BY THE USER ID
//    public ArrayList<Baskets> getBasketsByUserId(int userId) {
//        ArrayList<Baskets> baskets = new ArrayList<>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM "+ tblBasket +" WHERE "+ UserID +" = " + userId;
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                String basketId = cursor.getString(cursor.getColumnIndexOrThrow(BasketID));
//                int productId = cursor.getInt(cursor.getColumnIndexOrThrow(ProductId));
//
//                Baskets basket = new Baskets(Integer.parseInt(basketId), userId, productId);
//                baskets.add(basket);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//
//        return baskets;
//    }
    // https://stackoverflow.com/questions/68701475/android-password-hash
    // METHOD FOR HASHING PASSWORD
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Users checkUsernamePassword(String email1, String pass1) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + tblUsers + " WHERE " + UserEmail + "= '" + email1 + "' AND " + UserPassword + "='" + pass1 + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {

            byte[] userImageInBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(UserImage));

            Bitmap userImage = null;
            if (userImageInBytes != null && userImageInBytes.length > 0) {
                userImage = BitmapFactory.decodeByteArray(userImageInBytes, 0, userImageInBytes.length);
            }
            Users u1 = new Users(UserID, UserFullName, UserEmail, DateRegistered, DateUpdated, UserPassword, UserHobbies, UserPostcode, UserAddress, UserType, UserNumber, userImage);
            u1.setId(cursor.getString(0));
            u1.setFullName(cursor.getString(1));
            u1.setEmail(cursor.getString(3));
            u1.setDateRegistered(cursor.getString(4));
            u1.setDateUpdated(cursor.getString(5));
            u1.setPassword(cursor.getString(2));
            u1.setHobbies(cursor.getString(8));
            u1.setPostCode(cursor.getString(7));
            u1.setAddress(cursor.getString(6));
            u1.setUserImage(userImage);
            u1.setUserNumber(cursor.getString(10));
            u1.setUserType(cursor.getString(11));
            return u1;
        } else {
            return null;
        }
    }
}
