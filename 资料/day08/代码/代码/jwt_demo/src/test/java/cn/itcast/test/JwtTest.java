package cn.itcast.test;

import cn.hutool.core.io.FileUtil;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试jwt令牌
 */
public class JwtTest {
    //通过jjwt生成和解析jwt令牌，不使用签名算法
    @Test
    public void test1(){
        //用于封装jwt的header部分
        Map header = new HashMap();
        header.put("alg","none");//不使用签名算法
        header.put("typ","JWT");//指定令牌的类型，如果是jwt令牌统一都写为JWT

        //用于封装jwt的body部分
        Map body = new HashMap();
        body.put("userId","100");
        body.put("account","admin");
        body.put("role","admin");

        //使用jjwt提供的API生成jwt令牌
        String jwt = Jwts.builder().setHeader(header).setClaims(body).setId("101").compact();
        System.out.println(jwt);

        //使用jjwt提供的API解析jwt令牌
        Jwt result = Jwts.parser().parse(jwt);
        Header header1 = result.getHeader();
        Object body1 = result.getBody();
        System.out.println(header1);
        System.out.println(body1);

    }

    //通过jjwt生成和解析jwt令牌，使用HS256签名算法
    @Test
    public void test2(){
        //用于封装jwt的header部分
        Map header = new HashMap();
        header.put("alg", SignatureAlgorithm.HS256.getValue());//使用HS256签名算法
        header.put("typ","JWT");//指定令牌的类型，如果是jwt令牌统一都写为JWT

        //用于封装jwt的body部分
        Map body = new HashMap();
        body.put("userId","100");
        body.put("account","admin");
        body.put("role","admin");

        //使用jjwt提供的API生成jwt令牌
        String jwt = Jwts.builder().setHeader(header).setClaims(body).setId("101").signWith(SignatureAlgorithm.HS256,"itcast").compact();
        System.out.println(jwt);

        //使用jjwt提供的API解析jwt令牌
        Jwt result = Jwts.parser().setSigningKey("itcast").parse(jwt);
        Header header1 = result.getHeader();
        Object body1 = result.getBody();
        System.out.println(header1);
        System.out.println(body1);
    }

    //生成一对公钥和私钥，用于RS256算法
    @Test
    public void test3() throws Exception{
        //自定义 随机密码,  请修改这里
        String password = "itcast";

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(password.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();

        FileUtil.writeBytes(publicKeyBytes, "d:\\pub.key");
        FileUtil.writeBytes(privateKeyBytes, "d:\\pri.key");
    }

    //通过jjwt生成和解析jwt令牌，使用RS256签名算法
    @Test
    public void test4() throws Exception{
        //用于封装jwt的header部分
        Map header = new HashMap();
        header.put("alg", SignatureAlgorithm.RS256.getValue());//使用RS256签名算法
        header.put("typ","JWT");//指定令牌的类型，如果是jwt令牌统一都写为JWT

        //用于封装jwt的body部分
        Map body = new HashMap();
        body.put("userId","100");
        body.put("account","admin");
        body.put("role","admin");

        //私钥，生成jwt令牌时需要使用私钥
        InputStream resourceAsStream =
                this.getClass().getClassLoader().getResourceAsStream("pri.key");
        DataInputStream dis = new DataInputStream(resourceAsStream);
        byte[] keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);

        //使用jjwt提供的API生成jwt令牌
        String jwt = Jwts.
                        builder().
                        setHeader(header).
                        setClaims(body).
                        setId("101").
                        signWith(SignatureAlgorithm.RS256,privateKey)
                        .compact();
        System.out.println(jwt);

        //使用jjwt提供的API解析jwt令牌
        resourceAsStream =
                this.getClass().getClassLoader().getResourceAsStream("pub.key");
        dis = new DataInputStream(resourceAsStream);
        keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        X509EncodedKeySpec spec2 = new X509EncodedKeySpec(keyBytes);
        kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(spec2);

        Jwt result = Jwts.parser().setSigningKey(publicKey).parse(jwt);
        Header header1 = result.getHeader();
        Object body1 = result.getBody();
        System.out.println(header1);
        System.out.println(body1);
    }
}
