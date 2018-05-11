package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout;

/**
 * Created by sajjad on 7/12/2017.
 */

public class Url {
    public static final String MEDIA_SERVER_ADDRESS         = "http://media.sajjadyosefi.ir/";
    public static final String MAIN_SERVER_ADDRESS          = "http://test.sajjadyosefi.ir/";
    //public static final String MAIN_SERVER_ADDRESS          = "http://servertubeless.media.sajjadyosefi.ir/";
    public static final String CONFIG_SERVER_ADDRESS        = "http://test.sajjadyosefi.ir/";

    static final String SITE_ADDRESS = "";


    //static final String POST_RECEIVE_SERVER_CONFIG            =  Url.CONFIG_SERVER_ADDRESS + "Services/TubelessPrice/sbankconfig.aspx";
    static final String POST_RECEIVE_SERVER_CONFIG2             = Url.MAIN_SERVER_ADDRESS + "Config/config2";
    static final String POST_RECEIVE_SERVER_CONFIG3             = Url.MAIN_SERVER_ADDRESS + "Config/config3";

    static final String POST_ESTELAM                            = Url.CONFIG_SERVER_ADDRESS + "Services/TubelessPrice/estelam.aspx";
    static final String POST_CONFIRM_PAYMENT                    = Url.CONFIG_SERVER_ADDRESS + "Services/TubelessPrice/confirmPayment.aspx";
    //static final String POST_RECEIVE_TIMELINE                 = Url.CONFIG_SERVER_ADDRESS + "Services/TubelessPrice/tubelessTimeline.aspx?index=%s&count=10";

    //static final String POST_RECEIVE_TIMELINE                  = "http://price.sajjadyosefi.ir/WebService/tubelessTimeline.aspx?index=%s&count=10";
    static final String POST_RECEIVE_TIMELINE                   = Url.MAIN_SERVER_ADDRESS + "timeline/mainlist?index=%s&count=10";

    static final String POST_LOGIN                              = Url.MAIN_SERVER_ADDRESS + "Account/Login";
    static final String POST_REGISTER                           = Url.MAIN_SERVER_ADDRESS + "Account/register";
    static final String POST_DEVICE_REGISTER                    = Url.MAIN_SERVER_ADDRESS + "Account/deviceregister";
    static final String POST_CHANGE_PASSWORD                    = Url.MAIN_SERVER_ADDRESS + "Account/ChangePassword";
    static final String POST_SEND_MESSAGE_TO_SERVER             = Url.MAIN_SERVER_ADDRESS + "Messaging/ContactUs";

    static final String GET_CAR_CATEGORIES_BY_COMPANY           = Url.MAIN_SERVER_ADDRESS + "Config/carCategoriesByCompany";
    static final String GET_CAR_CATEGORIES_BY_COMPANY_RECENTLY  = Url.MAIN_SERVER_ADDRESS + "Config/carCategoriesByCompanyRecently";
    public static final String POST_ADD_CAR_PICTURE             = Url.MEDIA_SERVER_ADDRESS + "UploadMediaUsers.aspx";
    public static final String POST_ADD_YAFTE                   = Url.MEDIA_SERVER_ADDRESS + "UploadMediaUsers.aspx";
    public static final String GET_SEARCH_YAFTE                 = Url.MAIN_SERVER_ADDRESS + "Yafte/Search?Title=%s&HeaderID=%s";

    static final String GET_RECEIVE_BLOG_ITEMS                  = Url.MAIN_SERVER_ADDRESS + "Blog/getBlogList?CategoryId=%s&index=%s&Count=10";
    static final String GET_RECEIVE_BLOG_ITEMS_NEW              = Url.MAIN_SERVER_ADDRESS + "Blog/getBlogList?index=%s&Count=10";
    static final String GET_RECEIVE_BLOG_ITEM                   = Url.MAIN_SERVER_ADDRESS + "Blog/getBlog?blogid=%s&LoggedinUserId=%s";
    static final String POST_CHANGE_BLOG_ITEM_FAVOURITE_STATUS  = Url.MAIN_SERVER_ADDRESS + "Blog/newBlogFavorativeStatus";



    public static final String POST_PUSH                               = "http://fcm.googleapis.com/fcm/send";
}
