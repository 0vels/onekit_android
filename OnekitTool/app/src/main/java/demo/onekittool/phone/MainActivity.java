package demo.onekittool.phone;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.onekit.AJAX;
import cn.onekit.APP;
import cn.onekit.ARRAY;
import cn.onekit.ASSET;
import cn.onekit.CALLBACK;
import cn.onekit.CALLBACK0;
import cn.onekit.CALLBACK1;
import cn.onekit.COLOR;
import cn.onekit.CONFIG;
import cn.onekit.DATA;
import cn.onekit.DEVICE;
import cn.onekit.FSO;
import cn.onekit.JSON;
import cn.onekit.LBS;
import cn.onekit.LOADING;
import cn.onekit.MEDIA;
import cn.onekit.MESSAGE;
import cn.onekit.OS;
import cn.onekit.PASSWORD;
import cn.onekit.PATH;
import cn.onekit.RES;
import cn.onekit.STRING;
import cn.onekit.UI;
import cn.onekit.WEB;
import cn.onekit.XML;
public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private WebView webView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.mImg);
        webView = (WebView) findViewById(R.id.webView);
        textView = (TextView) findViewById(R.id.text1);
//        testJSON();    //已完成
//        testXML();     //已完成
//        configTest();  //已完成
//        stringTest();  //已完成
//        dataTest();    //已完成
//        colorTest();   //已完成
//        testArray();   //已完成
//        testPath();    //已完成
        testAPP();
//        testLoading();    //已完成
//        testMessage();       //已完成
//        testWeb();            //已完成
//        testFSO();            //已完成
//        testRES();            //已完成
//        testAssets();         //已完成
//        testDevice();         //已完成
//        testAJAX();           //已完成
//        testOS();             //已完成
//        testUI();
//        testLBS();
//        testPASSWORD();       //已完成
//        testMEDIA();
    }

    private void testMEDIA() {
        MEDIA.openCamera(this, new CALLBACK1<Bitmap>() {
            @Override
            public void run(Bitmap arg1) {
                Log.e("---------",arg1.toString());
                image.setImageBitmap(arg1);
            }
        }, new CALLBACK0() {
            @Override
            public void run() {
                Log.e("---------","没有相片");
            }
        });
    }

    private void testPASSWORD() {
        String str = "abcde";
        Log.e("------------MD5:",PASSWORD.MD5(str));
        Log.e("------------hash1:",PASSWORD.Hash1(str));

    }

    private void testLBS() {
        LBS.getLocation(this, new CALLBACK1<String>() {
            @Override
            public void run(String arg1) {
                Log.e("-----------",arg1);
            }
        }, new CALLBACK0() {
            @Override
            public void run() {

            }
        });
    }

    private void testUI() {
        Document document = UI.load("D:/activity_main.xml");
        Log.e("-------",XML.stringify(document));
    }

    private void testOS() {
        Log.e("平台名：", OS.platform());
        Log.e("版本号：", OS.version());
        Log.e("内核：", OS.Kernel());
    }

    private void testAJAX() {
        String url = "http://camixxx.win/test/_data/";

       /* AJAX.getVoid(url, null, new CALLBACK0() {
            @Override
            public void run() {
                Log.e("getVoid:","返回成功");
            }
        }, new CALLBACK1<Exception>() {
            @Override
            public void run(Exception arg1) {
                Log.e("get:",arg1+"");
            }
        }, AJAX.Mode.GET);*/

       /* AJAX.getString(url + "demo.txt", null, new CALLBACK1<String>() {
            @Override
            public void run(String arg1) {
                Log.e("getString:",arg1);
            }
        }, new CALLBACK1<Exception>() {
            @Override
            public void run(Exception arg1) {
                Log.e("getStringException:",arg1.toString());
            }
        }, AJAX.Mode.GET);*/

       /* AJAX.getXML(url + "test.xml", null, new CALLBACK1<Document>() {
            @Override
            public void run(Document arg1) {
                Log.e("getXML:", XML.stringify(arg1));
            }
        }, new CALLBACK1<Exception>() {
            @Override
            public void run(Exception arg1) {
                Log.e("getXMLException:",arg1.toString());
            }
        }, AJAX.Mode.GET);*/

        /*AJAX.getJSONs(url + "demo.json", null, new CALLBACK1<JSONArray>() {
            @Override
            public void run(JSONArray arg1) {
                Log.e("getJSONs:", JSON.stringify(arg1));
            }
        }, new CALLBACK1<Exception>() {
            @Override
            public void run(Exception arg1) {
                Log.e("getJSONsException:",arg1.toString());
            }
        }, AJAX.Mode.GET);*/

        /*AJAX.getImage(url + "demo.jpg", null, new CALLBACK1<Bitmap>() {
            @Override
            public void run(Bitmap arg1) {
                image.setImageBitmap(arg1);

            }
        }, new CALLBACK1<Exception>() {
            @Override
            public void run(Exception arg1) {
                Log.e("getImagException:",arg1.toString());
            }
        }, AJAX.Mode.GET);*/

        AJAX.getBase64(url + "demo.jpg", null, new CALLBACK1<String>() {
            @Override
            public void run(String arg1) {
                DATA.base642Image(arg1, new CALLBACK1<Bitmap>() {
                    @Override
                    public void run(Bitmap arg1) {
                        image.setImageBitmap(arg1);
                    }
                });
            }
        }, new CALLBACK1<Exception>() {
            @Override
            public void run(Exception arg1) {

            }
        }, AJAX.Mode.GET);

        /*AJAX.getBytes(url + "demo.txt", null, new CALLBACK1<byte[]>() {
            @Override
            public void run(byte[] arg1) {
                Log.e("getBytes:",new String(arg1));
            }
        }, new CALLBACK1<Exception>() {
            @Override
            public void run(Exception arg1) {
                Log.e("getBytesException:",arg1+"");
            }
        }, AJAX.Mode.GET);*/

    }

    private void testDevice() {
        Log.e("getAvailMemory:", DEVICE.getAvailMemory(this));
        Log.e("getID:",DEVICE.getID(this));
        Log.e("getMemory:",DEVICE.getMemory(this));
        Log.e("getCPU:",DEVICE.getCPU());
        Log.e("getNetwork:",DEVICE.getNetwork(this));
    }

    private void testAssets() {
        Log.e("loadString:", ASSET.loadString(this,"test.txt"));
        byte[] bytes = ASSET.loadData(this, "test.txt");
        Log.e("loadData:",new String(bytes));
        Log.e("loadJSON:",ASSET.loadJSONs(this,"demo.json")+"");
        Document document = ASSET.loadXML(this, "test.xml");
        Log.e("loadXML:", XML.stringify(document));
        Bitmap bitmap = ASSET.loadImage(this, "demo.png");
        image.setImageBitmap(bitmap);
    }

    private void testRES() {
        RES.loadLocalize(this,"app_name");
        BitmapDrawable drawable = RES.loadImage(this, "demo");
        image.setImageBitmap(drawable.getBitmap());
    }

    private void testFSO() {
        Bitmap  bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.demo);
        /*DATA.image2bytes(bitmap, new CALLBACK1<byte[]>() {
            @Override
            public void run(byte[] arg1) {
                FSO.saveBytes(MainActivity.this, "bytes.txt", arg1, new CALLBACK1<Boolean>() {
                    @Override
                    public void run(Boolean arg1) {
                        Log.e("---saveBytes:",arg1 +"");
                    }
                });
            }
        });

        FSO.loadBytes(this, "bytes.txt", new CALLBACK1<byte[]>() {
            @Override
            public void run(byte[] arg1) {
                DATA.bytes2image(arg1, new CALLBACK1<Bitmap>() {
                    @Override
                    public void run(Bitmap arg1) {
                        image.setImageBitmap(arg1);
                    }
                });
            }
        });*/


        /*FSO.saveImage(this, "img.png", bitmap, new CALLBACK1<Boolean>() {
            @Override
            public void run(Boolean arg1) {
                Log.e("保存图片是否成功：",arg1+"");
//                Log.e("删除",FSO.delete(MainActivity.this,"/aa/jimg.png")+"");
                Log.e("是否存在：",FSO.isExist(MainActivity.this,"img.png") + "");
                FSO.loadImage(MainActivity.this, "img.png", new CALLBACK1<Bitmap>() {
                    @Override
                    public void run(Bitmap arg1) {
                        image.setImageBitmap(arg1);
                    }
                });
            }
        });*/


       /* JSONArray jsonArray = ASSET.loadJSONs(this, "demo.json");
        FSO.saveJSONs(this, "jsonArray.json", jsonArray, new CALLBACK1<Boolean>() {
            @Override
            public void run(Boolean arg1) {
                Log.e("saveJSONs是否成功：",arg1+"");
            }
        });
//        FSO.delete(this,"jsonArray.json");
        Log.e("文件是否存在",FSO.isExist(this,"jsonArray.json")+"");
        FSO.loadJSONs(this, "jsonArray.json", new CALLBACK1<JSONArray>() {
            @Override
            public void run(JSONArray arg1) {
                Log.e("loadJSONs:",JSON.stringify(arg1));
            }
        });*/


        /*JSONObject jsonObject = ASSET.loadJSON(this, "demo.json");
        FSO.saveJSON(this, "jsonObject.json", jsonObject, new CALLBACK1<Boolean>() {
            @Override
            public void run(Boolean arg1) {
                Log.e("saveJSONs是否成功：",arg1+"");
            }
        });
//        FSO.delete(this,"jsonObject.json");
        Log.e("文件是否存在",FSO.isExist(this,"jsonObject.json")+"");
        FSO.loadJSON(this, "jsonObject.json", new CALLBACK1<JSONObject>() {
            @Override
            public void run(JSONObject arg1) {
                Log.e("JSON:",JSON.stringify(arg1));
            }
        });*/


       /* DATA.image2data(bitmap, new CALLBACK1<InputStream>() {
            @Override
            public void run(InputStream arg1) {
                FSO.saveData(MainActivity.this, "Input.text", arg1, new CALLBACK1<Boolean>() {
                    @Override
                    public void run(Boolean arg1) {
                        Log.e("saveData:",arg1+"");
                    }
                });
            }
        });
        FSO.loadData(this, "Input.text", new CALLBACK1<InputStream>() {
            @Override
            public void run(InputStream arg1) {
                DATA.data2Image(arg1, new CALLBACK1<Bitmap>() {
                    @Override
                    public void run(Bitmap arg1) {
                        image.setImageBitmap(arg1);
                    }
                });
            }
        });*/
    }

    private void testWeb() {
        WEB.load("http://www.baidu.com",null,webView);
    }

    private void testMessage() {
        MESSAGE.receive(this, "msg1", new CALLBACK<Map>() {
            @Override
            public void run(boolean isError, Map result) {
                Iterator<Map.Entry<Object, Object>> it = result.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Object, Object> entry = it.next();
                    Log.e("单个通知：","key= " + entry.getKey() + ",value= " + entry.getValue());
                }

            }
        },"11".hashCode());
        HashMap<String, Object> map = new HashMap<>();
        map.put("var","a");
        map.put("num",1);
        map.put("name","张");
        MESSAGE.send(this,"msg1",map,"11".hashCode());


    }

    private void testLoading() {
        LOADING.show(this);
    }

    private void testAPP() {
//        Log.e("--APP--", APP.getID(this));
//        Log.e("--Version--",APP.getVersion(this));
//        APP.goUrl(this,"http://www.baidu.com");
//        APP.goEmail(MainActivity.this,"386486519@qq.com","哈哈哈","发送成功了；");
        APP.goPhone(this,"10086");
//        APP.goSMS(this,"10086","你好");
//        APP.openEmail(this,"386486519@qq.com","你好","hahh");
//        APP.openPhone(this,"10086");
//        APP.openSMS(this,"10086","ninini");
    }

    private void testPath() {
        String str = "C:/www/svn/abc/a.txt";
        Log.e("文件名：", PATH.name(str));
        Log.e("文件夹：",PATH.folder(str));
        Log.e("扩展名：",PATH.ext(str));
    }

    private void testArray() {
       /* ArrayList<People> arrayList = new ArrayList<>();
        People p1 = new People("123","张");
        People p2 = new People("456","王");
        arrayList.add(p1);
        arrayList.add(p2);
        Log.e("find()",ARRAY.find(arrayList,p1,"123")+"");
        Log.e("contains()",ARRAY.contains(arrayList,p1,"123")+"");*/
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("a");
        strings.add("b");
        strings.add("c");
        Log.e("---", ARRAY.find(strings,"1",null)+"");
        Log.e("---",ARRAY.contains(strings,"1",null)+"");
    }

    private void colorTest() {
        String str = "#FF880080";
        Integer integer = COLOR.fromString(str);
        String toString = COLOR.toString(integer);
        String toRGB = COLOR.toRGBA(integer);
        Log.e("toRGB--",toRGB);
        Log.e("toString--",toString);
    }

    private void dataTest() {
        Bitmap  bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.demo);
        DATA.image2data(bitmap, new CALLBACK1<InputStream>() {
            @Override
            public void run(InputStream arg1) {
                DATA.data2bytes(arg1, new CALLBACK1<byte[]>() {
                    @Override
                    public void run(byte[] arg1) {
                        DATA.bytes2base64(arg1, new CALLBACK1<String>() {
                            @Override
                            public void run(String arg1) {
                                DATA.base642bytes(arg1, new CALLBACK1<byte[]>() {
                                    @Override
                                    public void run(byte[] arg1) {
                                        DATA.bytes2image(arg1, new CALLBACK1<Bitmap>() {
                                            @Override
                                            public void run(Bitmap arg1) {
                                                image.setImageBitmap(arg1);
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void testXML() {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><recipe type=\"dessert\"><recipename cuisine=\"american\" servings=\"1\">Ice Cream Sundae</recipename><preptime>5 minutes</preptime></recipe>\n";
        Document parse = XML.parse(str);
//        Log.e("XML反序列化--", parse +"");
        Log.e("XML序列化--",XML.stringify(parse));
    }

    private void testJSON() {
        JSONObject json = JSON.parse("{ \"people\": [ \n" +
                "\n" +
                "{ \"firstName\": \"Brett\", \"lastName\":\"McLaughlin\", \"email\": \"aaaa\" }, \n" +
                "\n" +
                "{ \"firstName\": \"Jason\", \"lastName\":\"Hunter\", \"email\": \"bbbb\"}, \n" +
                "\n" +
                "{ \"firstName\": \"Elliotte\", \"lastName\":\"Harold\", \"email\": \"cccc\" } \n" +
                "\n" +
                "]} ");
        String stringify = JSON.stringify(json);
        Log.e("序列化--",stringify);
    }

    /**
     * 测试String类
     */
    private void stringTest() {
        String  str = "OneKit测试oneKit要哭了oneKit三胞胎";
        String value = "oneKit";
        Log.e("isEmpty ", STRING.isEmpty(str)+"");
        Log.e("indexOf ",STRING.indexOf(str,value)+"");
        Log.e("endWith ",STRING.endWith(str,value)+"");
        Log.e("startWith ",STRING.startWith(str,value)+"");;
        Log.e("firstUpper ",STRING.firstUpper(str)+"");
        Log.e("lastIndexOf ",STRING.lastIndexOf(str,value)+"");
        Log.e("size ",STRING.size(str,12.0f)+"");
    }

    /**
     * 测试CONFIG类
     */
    private void configTest() {
        //测试setXML（）和getXML（）
       /* String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><recipe type=\"dessert\"><recipename cuisine=\"american\" servings=\"1\">Ice Cream Sundae</recipename><preptime>5 minutes</preptime></recipe>\n";
        Document document = XML.parse(str);
        CONFIG.setXML(this,"XML",document);
//        CONFIG.clearData(this);
        Document xml = CONFIG.getXML(this, "XML");
        String stringify = XML.stringify(xml);
        textView.setText(stringify);
        Log.e("XML--",stringify);*/

       /* Bitmap  bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.demo);
        DATA.image2data(bitmap, new CALLBACK1<InputStream>() {
            @Override
            public void run(InputStream arg1) {
                CONFIG.setData(MainActivity.this,"input",arg1);
            }
        });
        final InputStream input = CONFIG.getData(this, "input");
        DATA.data2Image(input, new CALLBACK1<Bitmap>() {
            @Override
            public void run(Bitmap arg1) {
                image.setImageBitmap(arg1);
            }
        });*/

        //测试setImage()和getImage()
       /*
        CONFIG.setImage(this,"img",bitmap);
        Bitmap img = CONFIG.getImage(this, "img");
        image.setImageBitmap(img);*/

        //测试setData()和getData()
       /* byte[] bytes = new byte[]{97,98,99,100};
        CONFIG.setBytes(this,"byte",bytes);
        Log.e("--byte[]--", Arrays.toString(CONFIG.getBytes(this,"byte")));*/

        //测试setJSONObject()和getJSONObject()
       /* JSONObject jsonObject = ASSET.loadJSON(this, "demo.json");

         CONFIG.setJSON(this,"json",jsonObject);
        JSONObject json = CONFIG.getJSON(this, "json");
        Log.e("getJson:",json.toString());*/

        /*CONFIG.setJSONs(this,"JsonArray",jsonArray);
        JSONArray array = CONFIG.getJSONs(this, "JsonArray");
        String stringify = JSON.stringify(array);
        Log.e("==================",stringify);*/

        /*Bitmap  bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.demo);
        DATA.image2bytes(bitmap, new CALLBACK1<byte[]>() {
            @Override
            public void run(byte[] arg1) {
                CONFIG.setBytes(MainActivity.this,"byte",arg1);
            }
        });

        byte[] bytes = CONFIG.getBytes(this, "byte");
        DATA.bytes2image(bytes, new CALLBACK1<Bitmap>() {
            @Override
            public void run(Bitmap arg1) {
                image.setImageBitmap(arg1);
            }
        });*/
    }
}
