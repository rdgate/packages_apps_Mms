package com.android.mms.transaction;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import com.google.android.mms.ContentType;
import com.google.android.mms.pdu.GenericPdu;
import com.google.android.mms.pdu.CharacterSets;
import com.google.android.mms.pdu.PduBody;
import com.google.android.mms.pdu.PduPart;
import com.google.android.mms.pdu.RetrieveConf;
import com.google.android.mms.pdu.SendReq;

import android.util.Log;

public class ConvUtils {
    private static final String TAG = "ConvUtils";
    private static final boolean LOCAL_LOGV = true;

    private static final Map<Integer, String> SBSJISUnicodeMap = new HashMap<Integer, String>() {
        {
            put(0xF7B0, "\u0023\u20E3");
            put(0xF7C5, "\u0030\u20E3");
            put(0xF7BC, "\u0031\u20E3");
            put(0xF7BD, "\u0032\u20E3");
            put(0xF7BE, "\u0033\u20E3");
            put(0xF7BF, "\u0034\u20E3");
            put(0xF7C0, "\u0035\u20E3");
            put(0xF7C1, "\u0036\u20E3");
            put(0xF7C2, "\u0037\u20E3");
            put(0xF7C3, "\u0038\u20E3");
            put(0xF7C4, "\u0039\u20E3");
            put(0xF7EE, "\u00A9");
            put(0xF7EF, "\u00AE");
            put(0xFBD7, "\u2122");
            put(0xF7D7, "\u2196");
            put(0xF7D6, "\u2197");
            put(0xF7D8, "\u2198");
            put(0xF7D9, "\u2199");
            put(0xF7DC, "\u23E9");
            put(0xF7DD, "\u23EA");
            put(0xF7DA, "\u25B6");
            put(0xF7DB, "\u25C0");
            put(0xF98B, "\u2600");
            put(0xF98A, "\u2601");
            put(0xF949, "\u260E");
            put(0xF98C, "\u2614");
            put(0xF986, "\u2615");
            put(0xF94F, "\u261D");
            put(0xFB54, "\u263A");
            put(0xF7DF, "\u2648");
            put(0xF7E0, "\u2649");
            put(0xF7E1, "\u264A");
            put(0xF7E2, "\u264B");
            put(0xF7E3, "\u264C");
            put(0xF7E4, "\u264D");
            put(0xF7E5, "\u264E");
            put(0xF7E6, "\u264F");
            put(0xF7E7, "\u2650");
            put(0xF7E8, "\u2651");
            put(0xF7E9, "\u2652");
            put(0xF7EA, "\u2653");
            put(0xF7AE, "\u2660");
            put(0xF7AF, "\u2663");
            put(0xF7AC, "\u2665");
            put(0xF7AD, "\u2666");
            put(0xF763, "\u2668");
            put(0xF7AA, "\u267F");
            put(0xF7F2, "\u26A0");
            put(0xF77D, "\u26A1");
            put(0xF958, "\u26BD");
            put(0xF956, "\u26BE");
            put(0xF989, "\u26C4");
            put(0xF7EB, "\u26CE");
            put(0xF977, "\u26EA");
            put(0xF761, "\u26F2");
            put(0xF954, "\u26F3");
            put(0xF95C, "\u26F5");
            put(0xF762, "\u26FA");
            put(0xF97A, "\u26FD");
            put(0xF9B3, "\u2702");
            put(0xF95D, "\u2708");
            put(0xF950, "\u270A");
            put(0xF952, "\u270B");
            put(0xF951, "\u270C");
            put(0xF9CE, "\u2728");
            put(0xF7A6, "\u2733");
            put(0xF7A5, "\u2734");
            put(0xF9D3, "\u274C");
            put(0xF960, "\u2753");
            put(0xF9D6, "\u2754");
            put(0xF9D7, "\u2755");
            put(0xF961, "\u2757");
            put(0xF962, "\u2764");
            put(0xF7D4, "\u27A1");
            put(0xF7D5, "\u2B05");
            put(0xF7D2, "\u2B06");
            put(0xF7D3, "\u2B07");
            put(0xF9CF, "\u2B50");
            put(0xF9D2, "\u2B55");
            put(0xF76C, "\u303D");
            put(0xF9AD, "\u3297");
            put(0xF9B5, "\u3299");
            put(0xF76D, "\uD83C\uDC04");
            put(0xFBD2, "\uD83C\uDD70");
            put(0xFBD3, "\uD83C\uDD71");
            put(0xFBD5, "\uD83C\uDD7E");
            put(0xF790, "\uD83C\uDD7F");
            put(0xFBD4, "\uD83C\uDD8E");
            put(0xF7B4, "\uD83C\uDD92");
            put(0xF7C9, "\uD83C\uDD94");
            put(0xF7B2, "\uD83C\uDD95");
            put(0xF7ED, "\uD83C\uDD97");
            put(0xF7B3, "\uD83C\uDD99");
            put(0xF76E, "\uD83C\uDD9A");
            put(0xFBB3, "\uD83C\uDDE8\uD83C\uDDF3");
            put(0xFBAE, "\uD83C\uDDE9\uD83C\uDDEA");
            put(0xFBB1, "\uD83C\uDDEA\uD83C\uDDF8");
            put(0xFBAD, "\uD83C\uDDEB\uD83C\uDDF7");
            put(0xFBB0, "\uD83C\uDDEC\uD83C\uDDE7");
            put(0xFBAF, "\uD83C\uDDEE\uD83C\uDDF9");
            put(0xFBAB, "\uD83C\uDDEF\uD83C\uDDF5");
            put(0xFBB4, "\uD83C\uDDF0\uD83C\uDDF7");
            put(0xFBB2, "\uD83C\uDDF7\uD83C\uDDFA");
            put(0xFBAC, "\uD83C\uDDFA\uD83C\uDDF8");
            put(0xF7A3, "\uD83C\uDE01");
            put(0xF7C8, "\uD83C\uDE02");
            put(0xF7B6, "\uD83C\uDE1A");
            put(0xF7CC, "\uD83C\uDE2F");
            put(0xF7CB, "\uD83C\uDE33");
            put(0xF7CA, "\uD83C\uDE35");
            put(0xF7B5, "\uD83C\uDE36");
            put(0xF7B7, "\uD83C\uDE37");
            put(0xF7B8, "\uD83C\uDE38");
            put(0xF7C7, "\uD83C\uDE39");
            put(0xF7CD, "\uD83C\uDE3A");
            put(0xF7C6, "\uD83C\uDE50");
            put(0xFB84, "\uD83C\uDF00");
            put(0xFB7C, "\uD83C\uDF02");
            put(0xFB8C, "\uD83C\uDF03");
            put(0xF98E, "\uD83C\uDF04");
            put(0xFB8A, "\uD83C\uDF05");
            put(0xF787, "\uD83C\uDF06");
            put(0xFB8B, "\uD83C\uDF07");
            put(0xFB8D, "\uD83C\uDF08");
            put(0xFB7E, "\uD83C\uDF0A");
            put(0xF98D, "\uD83C\uDF19");
            put(0xF9D5, "\uD83C\uDF1F");
            put(0xF9A7, "\uD83C\uDF34");
            put(0xF9A8, "\uD83C\uDF35");
            put(0xF9A4, "\uD83C\uDF37");
            put(0xF970, "\uD83C\uDF38");
            put(0xF972, "\uD83C\uDF39");
            put(0xF9A3, "\uD83C\uDF3A");
            put(0xF9A5, "\uD83C\uDF3B");
            put(0xFB85, "\uD83C\uDF3E");
            put(0xF750, "\uD83C\uDF40");
            put(0xF758, "\uD83C\uDF41");
            put(0xF759, "\uD83C\uDF42");
            put(0xFB88, "\uD83C\uDF43");
            put(0xF9E9, "\uD83C\uDF45");
            put(0xF9EA, "\uD83C\uDF46");
            put(0xF9E8, "\uD83C\uDF49");
            put(0xF9E6, "\uD83C\uDF4A");
            put(0xF9E5, "\uD83C\uDF4E");
            put(0xF9E7, "\uD83C\uDF53");
            put(0xF760, "\uD83C\uDF54");
            put(0xF9DD, "\uD83C\uDF58");
            put(0xF9E2, "\uD83C\uDF59");
            put(0xF9DE, "\uD83C\uDF5A");
            put(0xF9E1, "\uD83C\uDF5B");
            put(0xF9E0, "\uD83C\uDF5C");
            put(0xF9DF, "\uD83C\uDF5D");
            put(0xF9D9, "\uD83C\uDF5E");
            put(0xF9DB, "\uD83C\uDF5F");
            put(0xF9DC, "\uD83C\uDF61");
            put(0xF9E3, "\uD83C\uDF62");
            put(0xF9E4, "\uD83C\uDF63");
            put(0xF9DA, "\uD83C\uDF66");
            put(0xFB80, "\uD83C\uDF67");
            put(0xF987, "\uD83C\uDF70");
            put(0xF9EC, "\uD83C\uDF71");
            put(0xF9ED, "\uD83C\uDF72");
            put(0xF788, "\uD83C\uDF73");
            put(0xF984, "\uD83C\uDF74");
            put(0xF9D8, "\uD83C\uDF75");
            put(0xF9AB, "\uD83C\uDF76");
            put(0xF985, "\uD83C\uDF78");
            put(0xF988, "\uD83C\uDF7A");
            put(0xF9AC, "\uD83C\uDF7B");
            put(0xF9B4, "\uD83C\uDF80");
            put(0xF752, "\uD83C\uDF81");
            put(0xF9EB, "\uD83C\uDF82");
            put(0xFB86, "\uD83C\uDF83");
            put(0xF973, "\uD83C\uDF84");
            put(0xFB89, "\uD83C\uDF85");
            put(0xF757, "\uD83C\uDF86");
            put(0xFB81, "\uD83C\uDF87");
            put(0xF9B0, "\uD83C\uDF88");
            put(0xF9B2, "\uD83C\uDF89");
            put(0xF784, "\uD83C\uDF8C");
            put(0xFB76, "\uD83C\uDF8D");
            put(0xFB78, "\uD83C\uDF8E");
            put(0xFB7B, "\uD83C\uDF8F");
            put(0xFB83, "\uD83C\uDF90");
            put(0xFB87, "\uD83C\uDF91");
            put(0xFB7A, "\uD83C\uDF92");
            put(0xFB79, "\uD83C\uDF93");
            put(0xF764, "\uD83C\uDFA1");
            put(0xFB73, "\uD83C\uDFA2");
            put(0xF97C, "\uD83C\uDFA4");
            put(0xF97D, "\uD83C\uDFA5");
            put(0xFBA7, "\uD83C\uDFA6");
            put(0xF9AA, "\uD83C\uDFA7");
            put(0xFBA2, "\uD83C\uDFA8");
            put(0xFBA3, "\uD83C\uDFA9");
            put(0xF765, "\uD83C\uDFAB");
            put(0xF9C4, "\uD83C\uDFAC");
            put(0xF770, "\uD83C\uDFAF");
            put(0xF773, "\uD83C\uDFB0");
            put(0xFB6C, "\uD83C\uDFB1");
            put(0xF97E, "\uD83C\uDFB5");
            put(0xF9C6, "\uD83C\uDFB6");
            put(0xF981, "\uD83C\uDFB7");
            put(0xF982, "\uD83C\uDFB8");
            put(0xF983, "\uD83C\uDFBA");
            put(0xF955, "\uD83C\uDFBE");
            put(0xF953, "\uD83C\uDFBF");
            put(0xFB6A, "\uD83C\uDFC0");
            put(0xF772, "\uD83C\uDFC1");
            put(0xF755, "\uD83C\uDFC3");
            put(0xF957, "\uD83C\uDFC4");
            put(0xF771, "\uD83C\uDFC6");
            put(0xFB6B, "\uD83C\uDFC8");
            put(0xFB6D, "\uD83C\uDFCA");
            put(0xF976, "\uD83C\uDFE0");
            put(0xF978, "\uD83C\uDFE2");
            put(0xF794, "\uD83C\uDFE3");
            put(0xF796, "\uD83C\uDFE5");
            put(0xF78E, "\uD83C\uDFE6");
            put(0xF795, "\uD83C\uDFE7");
            put(0xF799, "\uD83C\uDFE8");
            put(0xFBA1, "\uD83C\uDFE9");
            put(0xF797, "\uD83C\uDFEA");
            put(0xF798, "\uD83C\uDFEB");
            put(0xFBA4, "\uD83C\uDFEC");
            put(0xFBA8, "\uD83C\uDFED");
            put(0xFBA5, "\uD83C\uDFEF");
            put(0xFBA6, "\uD83C\uDFF0");
            put(0xFBCD, "\uD83D\uDC0D");
            put(0xF774, "\uD83D\uDC0E");
            put(0xFBC9, "\uD83D\uDC11");
            put(0xFBC8, "\uD83D\uDC12");
            put(0xFBCE, "\uD83D\uDC14");
            put(0xFBCF, "\uD83D\uDC17");
            put(0xFBC6, "\uD83D\uDC18");
            put(0xF74A, "\uD83D\uDC19");
            put(0xFB82, "\uD83D\uDC1A");
            put(0xFBC5, "\uD83D\uDC1B");
            put(0xF959, "\uD83D\uDC1F");
            put(0xFBC2, "\uD83D\uDC20");
            put(0xFBC3, "\uD83D\uDC24");
            put(0xFBC1, "\uD83D\uDC26");
            put(0xF996, "\uD83D\uDC27");
            put(0xFBC7, "\uD83D\uDC28");
            put(0xFBD0, "\uD83D\uDC2B");
            put(0xFBC0, "\uD83D\uDC2C");
            put(0xF994, "\uD83D\uDC2D");
            put(0xFBCB, "\uD83D\uDC2E");
            put(0xF991, "\uD83D\uDC2F");
            put(0xFBCC, "\uD83D\uDC30");
            put(0xF990, "\uD83D\uDC31");
            put(0xF995, "\uD83D\uDC33");
            put(0xF95A, "\uD83D\uDC34");
            put(0xF749, "\uD83D\uDC35");
            put(0xF993, "\uD83D\uDC36");
            put(0xF74B, "\uD83D\uDC37");
            put(0xFBD1, "\uD83D\uDC38");
            put(0xFBC4, "\uD83D\uDC39");
            put(0xFBCA, "\uD83D\uDC3A");
            put(0xF992, "\uD83D\uDC3B");
            put(0xFB59, "\uD83D\uDC40");
            put(0xFB5B, "\uD83D\uDC42");
            put(0xFB5A, "\uD83D\uDC43");
            put(0xFB5C, "\uD83D\uDC44");
            put(0xF7CE, "\uD83D\uDC46");
            put(0xF7CF, "\uD83D\uDC47");
            put(0xF7D0, "\uD83D\uDC48");
            put(0xF7D1, "\uD83D\uDC49");
            put(0xF94D, "\uD83D\uDC4A");
            put(0xFB5E, "\uD83D\uDC4B");
            put(0xFB60, "\uD83D\uDC4C");
            put(0xF94E, "\uD83D\uDC4D");
            put(0xFB61, "\uD83D\uDC4E");
            put(0xFB5F, "\uD83D\uDC4F");
            put(0xFB62, "\uD83D\uDC50");
            put(0xF74E, "\uD83D\uDC51");
            put(0xF9B8, "\uD83D\uDC52");
            put(0xF9A2, "\uD83D\uDC54");
            put(0xF946, "\uD83D\uDC55");
            put(0xF9B9, "\uD83D\uDC57");
            put(0xF9C1, "\uD83D\uDC58");
            put(0xF9C2, "\uD83D\uDC59");
            put(0xF9C3, "\uD83D\uDC5C");
            put(0xF947, "\uD83D\uDC5F");
            put(0xF77E, "\uD83D\uDC60");
            put(0xF9BA, "\uD83D\uDC61");
            put(0xF9BB, "\uD83D\uDC62");
            put(0xFBD6, "\uD83D\uDC63");
            put(0xF941, "\uD83D\uDC66");
            put(0xF942, "\uD83D\uDC67");
            put(0xF944, "\uD83D\uDC68");
            put(0xF945, "\uD83D\uDC69");
            put(0xFB68, "\uD83D\uDC6B");
            put(0xF793, "\uD83D\uDC6E");
            put(0xFB69, "\uD83D\uDC6F");
            put(0xFBB5, "\uD83D\uDC71");
            put(0xFBB6, "\uD83D\uDC72");
            put(0xFBB7, "\uD83D\uDC73");
            put(0xFBB8, "\uD83D\uDC74");
            put(0xFBB9, "\uD83D\uDC75");
            put(0xFBBA, "\uD83D\uDC76");
            put(0xFBBB, "\uD83D\uDC77");
            put(0xFBBC, "\uD83D\uDC78");
            put(0xF75B, "\uD83D\uDC7B");
            put(0xF98F, "\uD83D\uDC7C");
            put(0xF74C, "\uD83D\uDC7D");
            put(0xF76B, "\uD83D\uDC7E");
            put(0xF75A, "\uD83D\uDC7F");
            put(0xF75C, "\uD83D\uDC80");
            put(0xF7F3, "\uD83D\uDC81");
            put(0xFBBE, "\uD83D\uDC82");
            put(0xFBBF, "\uD83D\uDC83");
            put(0xF9BC, "\uD83D\uDC84");
            put(0xF9BD, "\uD83D\uDC85");
            put(0xF9BE, "\uD83D\uDC86");
            put(0xF9BF, "\uD83D\uDC87");
            put(0xF9C0, "\uD83D\uDC88");
            put(0xF77B, "\uD83D\uDC89");
            put(0xF9AF, "\uD83D\uDC8A");
            put(0xF943, "\uD83D\uDC8B");
            put(0xF974, "\uD83D\uDC8D");
            put(0xF975, "\uD83D\uDC8E");
            put(0xF751, "\uD83D\uDC8F");
            put(0xF9A6, "\uD83D\uDC90");
            put(0xFB65, "\uD83D\uDC91");
            put(0xFB7D, "\uD83D\uDC92");
            put(0xF9C7, "\uD83D\uDC93");
            put(0xF963, "\uD83D\uDC94");
            put(0xF9C8, "\uD83D\uDC97");
            put(0xF9C9, "\uD83D\uDC98");
            put(0xF9CA, "\uD83D\uDC99");
            put(0xF9CB, "\uD83D\uDC9A");
            put(0xF9CC, "\uD83D\uDC9B");
            put(0xF9CD, "\uD83D\uDC9C");
            put(0xFB77, "\uD83D\uDC9D");
            put(0xF7A4, "\uD83D\uDC9F");
            put(0xF74F, "\uD83D\uDCA1");
            put(0xF9D4, "\uD83D\uDCA2");
            put(0xF9B1, "\uD83D\uDCA3");
            put(0xF77C, "\uD83D\uDCA4");
            put(0xF9D1, "\uD83D\uDCA6");
            put(0xF9D0, "\uD83D\uDCA8");
            put(0xF99B, "\uD83D\uDCA9");
            put(0xF78D, "\uD83D\uDCAA");
            put(0xF76F, "\uD83D\uDCB0");
            put(0xF78A, "\uD83D\uDCB1");
            put(0xF78B, "\uD83D\uDCB9");
            put(0xF75F, "\uD83D\uDCBA");
            put(0xF94C, "\uD83D\uDCBB");
            put(0xF75E, "\uD83D\uDCBC");
            put(0xF9B6, "\uD83D\uDCBD");
            put(0xF766, "\uD83D\uDCBF");
            put(0xF767, "\uD83D\uDCC0");
            put(0xF789, "\uD83D\uDCD6");
            put(0xF9A1, "\uD83D\uDCDD");
            put(0xF94B, "\uD83D\uDCE0");
            put(0xF78C, "\uD83D\uDCE1");
            put(0xF783, "\uD83D\uDCE2");
            put(0xF9B7, "\uD83D\uDCE3");
            put(0xF743, "\uD83D\uDCE9");
            put(0xF741, "\uD83D\uDCEB");
            put(0xF742, "\uD83D\uDCEE");
            put(0xF94A, "\uD83D\uDCF1");
            put(0xF744, "\uD83D\uDCF2");
            put(0xF7F0, "\uD83D\uDCF3");
            put(0xF7F1, "\uD83D\uDCF4");
            put(0xF7AB, "\uD83D\uDCF6");
            put(0xF948, "\uD83D\uDCF7");
            put(0xF76A, "\uD83D\uDCFA");
            put(0xF768, "\uD83D\uDCFB");
            put(0xF769, "\uD83D\uDCFC");
            put(0xF782, "\uD83D\uDD0A");
            put(0xF754, "\uD83D\uDD0D");
            put(0xF980, "\uD83D\uDD11");
            put(0xF785, "\uD83D\uDD12");
            put(0xF786, "\uD83D\uDD13");
            put(0xF9C5, "\uD83D\uDD14");
            put(0xF7EC, "\uD83D\uDD1D");
            put(0xF7A7, "\uD83D\uDD1E");
            put(0xF75D, "\uD83D\uDD25");
            put(0xF756, "\uD83D\uDD28");
            put(0xF753, "\uD83D\uDD2B");
            put(0xF7DE, "\uD83D\uDD2F");
            put(0xF7A9, "\uD83D\uDD30");
            put(0xF971, "\uD83D\uDD31");
            put(0xF7BA, "\uD83D\uDD32");
            put(0xF7BB, "\uD83D\uDD33");
            put(0xF7B9, "\uD83D\uDD34");
            put(0xF964, "\uD83D\uDD50");
            put(0xF965, "\uD83D\uDD51");
            put(0xF966, "\uD83D\uDD52");
            put(0xF967, "\uD83D\uDD53");
            put(0xF968, "\uD83D\uDD54");
            put(0xF969, "\uD83D\uDD55");
            put(0xF96A, "\uD83D\uDD56");
            put(0xF96B, "\uD83D\uDD57");
            put(0xF96C, "\uD83D\uDD58");
            put(0xF96D, "\uD83D\uDD59");
            put(0xF96E, "\uD83D\uDD5A");
            put(0xF96F, "\uD83D\uDD5B");
            put(0xF97B, "\uD83D\uDDFB");
            put(0xFBA9, "\uD83D\uDDFC");
            put(0xFBBD, "\uD83D\uDDFD");
            put(0xFB44, "\uD83D\uDE01");
            put(0xFB52, "\uD83D\uDE02");
            put(0xF998, "\uD83D\uDE03");
            put(0xFB55, "\uD83D\uDE04");
            put(0xFB45, "\uD83D\uDE09");
            put(0xF997, "\uD83D\uDE0A");
            put(0xFB4A, "\uD83D\uDE0C");
            put(0xF746, "\uD83D\uDE0D");
            put(0xFB42, "\uD83D\uDE0F");
            put(0xFB4E, "\uD83D\uDE12");
            put(0xF748, "\uD83D\uDE13");
            put(0xFB43, "\uD83D\uDE14");
            put(0xFB47, "\uD83D\uDE16");
            put(0xFB58, "\uD83D\uDE18");
            put(0xFB57, "\uD83D\uDE1A");
            put(0xF745, "\uD83D\uDE1C");
            put(0xFB49, "\uD83D\uDE1D");
            put(0xF999, "\uD83D\uDE1E");
            put(0xF99A, "\uD83D\uDE20");
            put(0xFB56, "\uD83D\uDE21");
            put(0xFB53, "\uD83D\uDE22");
            put(0xFB46, "\uD83D\uDE23");
            put(0xFB41, "\uD83D\uDE25");
            put(0xFB4B, "\uD83D\uDE28");
            put(0xFB48, "\uD83D\uDE2A");
            put(0xFB51, "\uD83D\uDE2D");
            put(0xFB4F, "\uD83D\uDE30");
            put(0xF747, "\uD83D\uDE31");
            put(0xFB50, "\uD83D\uDE32");
            put(0xFB4D, "\uD83D\uDE33");
            put(0xFB4C, "\uD83D\uDE37");
            put(0xFB63, "\uD83D\uDE45");
            put(0xFB64, "\uD83D\uDE46");
            put(0xFB66, "\uD83D\uDE47");
            put(0xFB67, "\uD83D\uDE4C");
            put(0xFB5D, "\uD83D\uDE4F");
            put(0xF74D, "\uD83D\uDE80");
            put(0xF95E, "\uD83D\uDE83");
            put(0xFB75, "\uD83D\uDE84");
            put(0xF95F, "\uD83D\uDE85");
            put(0xFB74, "\uD83D\uDE87");
            put(0xF979, "\uD83D\uDE89");
            put(0xF79A, "\uD83D\uDE8C");
            put(0xF791, "\uD83D\uDE8F");
            put(0xFB71, "\uD83D\uDE91");
            put(0xFB70, "\uD83D\uDE92");
            put(0xFB72, "\uD83D\uDE93");
            put(0xF79B, "\uD83D\uDE95");
            put(0xF95B, "\uD83D\uDE97");
            put(0xFB6E, "\uD83D\uDE99");
            put(0xFB6F, "\uD83D\uDE9A");
            put(0xF7A2, "\uD83D\uDEA2");
            put(0xF775, "\uD83D\uDEA4");
            put(0xF78F, "\uD83D\uDEA5");
            put(0xF777, "\uD83D\uDEA7");
            put(0xF9AE, "\uD83D\uDEAC");
            put(0xF7A8, "\uD83D\uDEAD");
            put(0xF776, "\uD83D\uDEB2");
            put(0xF7A1, "\uD83D\uDEB6");
            put(0xF778, "\uD83D\uDEB9");
            put(0xF779, "\uD83D\uDEBA");
            put(0xF792, "\uD83D\uDEBB");
            put(0xF77A, "\uD83D\uDEBC");
            put(0xF781, "\uD83D\uDEBD");
            put(0xF9A9, "\uD83D\uDEBE");
            put(0xF780, "\uD83D\uDEC0");
        }
    };

    static String[][] SBUnicodeMap = {
        { "\uE210", "\u0023\u20E3" },
        { "\uE225", "\u0030\u20E3" },
        { "\uE21C", "\u0031\u20E3" },
        { "\uE21D", "\u0032\u20E3" },
        { "\uE21E", "\u0033\u20E3" },
        { "\uE21F", "\u0034\u20E3" },
        { "\uE220", "\u0035\u20E3" },
        { "\uE221", "\u0036\u20E3" },
        { "\uE222", "\u0037\u20E3" },
        { "\uE223", "\u0038\u20E3" },
        { "\uE224", "\u0039\u20E3" },
        { "\uE24E", "\u00A9" },
        { "\uE24F", "\u00AE" },
        { "\uE537", "\u2122" },
        { "\uE237", "\u2196" },
        { "\uE236", "\u2197" },
        { "\uE238", "\u2198" },
        { "\uE239", "\u2199" },
        { "\uE23C", "\u23E9" },
        { "\uE23D", "\u23EA" },
        { "\uE23A", "\u25B6" },
        { "\uE23B", "\u25C0" },
        { "\uE04A", "\u2600" },
        { "\uE049", "\u2601" },
        { "\uE009", "\u260E" },
        { "\uE04B", "\u2614" },
        { "\uE045", "\u2615" },
        { "\uE00F", "\u261D" },
        { "\uE414", "\u263A" },
        { "\uE23F", "\u2648" },
        { "\uE240", "\u2649" },
        { "\uE241", "\u264A" },
        { "\uE242", "\u264B" },
        { "\uE243", "\u264C" },
        { "\uE244", "\u264D" },
        { "\uE245", "\u264E" },
        { "\uE246", "\u264F" },
        { "\uE247", "\u2650" },
        { "\uE248", "\u2651" },
        { "\uE249", "\u2652" },
        { "\uE24A", "\u2653" },
        { "\uE20E", "\u2660" },
        { "\uE20F", "\u2663" },
        { "\uE20C", "\u2665" },
        { "\uE20D", "\u2666" },
        { "\uE123", "\u2668" },
        { "\uE20A", "\u267F" },
        { "\uE252", "\u26A0" },
        { "\uE13D", "\u26A1" },
        { "\uE018", "\u26BD" },
        { "\uE016", "\u26BE" },
        { "\uE048", "\u26C4" },
        { "\uE24B", "\u26CE" },
        { "\uE037", "\u26EA" },
        { "\uE121", "\u26F2" },
        { "\uE014", "\u26F3" },
        { "\uE01C", "\u26F5" },
        { "\uE122", "\u26FA" },
        { "\uE03A", "\u26FD" },
        { "\uE313", "\u2702" },
        { "\uE01D", "\u2708" },
        { "\uE010", "\u270A" },
        { "\uE012", "\u270B" },
        { "\uE011", "\u270C" },
        { "\uE32E", "\u2728" },
        { "\uE206", "\u2733" },
        { "\uE205", "\u2734" },
        { "\uE333", "\u274C" },
        { "\uE020", "\u2753" },
        { "\uE336", "\u2754" },
        { "\uE337", "\u2755" },
        { "\uE021", "\u2757" },
        { "\uE022", "\u2764" },
        { "\uE234", "\u27A1" },
        { "\uE235", "\u2B05" },
        { "\uE232", "\u2B06" },
        { "\uE233", "\u2B07" },
        { "\uE32F", "\u2B50" },
        { "\uE332", "\u2B55" },
        { "\uE12C", "\u303D" },
        { "\uE30D", "\u3297" },
        { "\uE315", "\u3299" },
        { "\uE12D", "\uD83C\uDC04" },
        { "\uE532", "\uD83C\uDD70" },
        { "\uE533", "\uD83C\uDD71" },
        { "\uE535", "\uD83C\uDD7E" },
        { "\uE14F", "\uD83C\uDD7F" },
        { "\uE534", "\uD83C\uDD8E" },
        { "\uE214", "\uD83C\uDD92" },
        { "\uE229", "\uD83C\uDD94" },
        { "\uE212", "\uD83C\uDD95" },
        { "\uE24D", "\uD83C\uDD97" },
        { "\uE213", "\uD83C\uDD99" },
        { "\uE12E", "\uD83C\uDD9A" },
        { "\uE513", "\uD83C\uDDE8\uD83C\uDDF3" },
        { "\uE50E", "\uD83C\uDDE9\uD83C\uDDEA" },
        { "\uE511", "\uD83C\uDDEA\uD83C\uDDF8" },
        { "\uE50D", "\uD83C\uDDEB\uD83C\uDDF7" },
        { "\uE510", "\uD83C\uDDEC\uD83C\uDDE7" },
        { "\uE50F", "\uD83C\uDDEE\uD83C\uDDF9" },
        { "\uE50B", "\uD83C\uDDEF\uD83C\uDDF5" },
        { "\uE514", "\uD83C\uDDF0\uD83C\uDDF7" },
        { "\uE512", "\uD83C\uDDF7\uD83C\uDDFA" },
        { "\uE50C", "\uD83C\uDDFA\uD83C\uDDF8" },
        { "\uE203", "\uD83C\uDE01" },
        { "\uE228", "\uD83C\uDE02" },
        { "\uE216", "\uD83C\uDE1A" },
        { "\uE22C", "\uD83C\uDE2F" },
        { "\uE22B", "\uD83C\uDE33" },
        { "\uE22A", "\uD83C\uDE35" },
        { "\uE215", "\uD83C\uDE36" },
        { "\uE217", "\uD83C\uDE37" },
        { "\uE218", "\uD83C\uDE38" },
        { "\uE227", "\uD83C\uDE39" },
        { "\uE22D", "\uD83C\uDE3A" },
        { "\uE226", "\uD83C\uDE50" },
        { "\uE443", "\uD83C\uDF00" },
        { "\uE43C", "\uD83C\uDF02" },
        { "\uE44B", "\uD83C\uDF03" },
        { "\uE04D", "\uD83C\uDF04" },
        { "\uE449", "\uD83C\uDF05" },
        { "\uE146", "\uD83C\uDF06" },
        { "\uE44A", "\uD83C\uDF07" },
        { "\uE44C", "\uD83C\uDF08" },
        { "\uE43E", "\uD83C\uDF0A" },
        { "\uE04C", "\uD83C\uDF19" },
        { "\uE335", "\uD83C\uDF1F" },
        { "\uE307", "\uD83C\uDF34" },
        { "\uE308", "\uD83C\uDF35" },
        { "\uE304", "\uD83C\uDF37" },
        { "\uE030", "\uD83C\uDF38" },
        { "\uE032", "\uD83C\uDF39" },
        { "\uE303", "\uD83C\uDF3A" },
        { "\uE305", "\uD83C\uDF3B" },
        { "\uE444", "\uD83C\uDF3E" },
        { "\uE110", "\uD83C\uDF40" },
        { "\uE118", "\uD83C\uDF41" },
        { "\uE119", "\uD83C\uDF42" },
        { "\uE447", "\uD83C\uDF43" },
        { "\uE349", "\uD83C\uDF45" },
        { "\uE34A", "\uD83C\uDF46" },
        { "\uE348", "\uD83C\uDF49" },
        { "\uE346", "\uD83C\uDF4A" },
        { "\uE345", "\uD83C\uDF4E" },
        { "\uE347", "\uD83C\uDF53" },
        { "\uE120", "\uD83C\uDF54" },
        { "\uE33D", "\uD83C\uDF58" },
        { "\uE342", "\uD83C\uDF59" },
        { "\uE33E", "\uD83C\uDF5A" },
        { "\uE341", "\uD83C\uDF5B" },
        { "\uE340", "\uD83C\uDF5C" },
        { "\uE33F", "\uD83C\uDF5D" },
        { "\uE339", "\uD83C\uDF5E" },
        { "\uE33B", "\uD83C\uDF5F" },
        { "\uE33C", "\uD83C\uDF61" },
        { "\uE343", "\uD83C\uDF62" },
        { "\uE344", "\uD83C\uDF63" },
        { "\uE33A", "\uD83C\uDF66" },
        { "\uE43F", "\uD83C\uDF67" },
        { "\uE046", "\uD83C\uDF70" },
        { "\uE34C", "\uD83C\uDF71" },
        { "\uE34D", "\uD83C\uDF72" },
        { "\uE147", "\uD83C\uDF73" },
        { "\uE043", "\uD83C\uDF74" },
        { "\uE338", "\uD83C\uDF75" },
        { "\uE30B", "\uD83C\uDF76" },
        { "\uE044", "\uD83C\uDF78" },
        { "\uE047", "\uD83C\uDF7A" },
        { "\uE30C", "\uD83C\uDF7B" },
        { "\uE314", "\uD83C\uDF80" },
        { "\uE112", "\uD83C\uDF81" },
        { "\uE34B", "\uD83C\uDF82" },
        { "\uE445", "\uD83C\uDF83" },
        { "\uE033", "\uD83C\uDF84" },
        { "\uE448", "\uD83C\uDF85" },
        { "\uE117", "\uD83C\uDF86" },
        { "\uE440", "\uD83C\uDF87" },
        { "\uE310", "\uD83C\uDF88" },
        { "\uE312", "\uD83C\uDF89" },
        { "\uE143", "\uD83C\uDF8C" },
        { "\uE436", "\uD83C\uDF8D" },
        { "\uE438", "\uD83C\uDF8E" },
        { "\uE43B", "\uD83C\uDF8F" },
        { "\uE442", "\uD83C\uDF90" },
        { "\uE446", "\uD83C\uDF91" },
        { "\uE43A", "\uD83C\uDF92" },
        { "\uE439", "\uD83C\uDF93" },
        { "\uE124", "\uD83C\uDFA1" },
        { "\uE433", "\uD83C\uDFA2" },
        { "\uE03C", "\uD83C\uDFA4" },
        { "\uE03D", "\uD83C\uDFA5" },
        { "\uE507", "\uD83C\uDFA6" },
        { "\uE30A", "\uD83C\uDFA7" },
        { "\uE502", "\uD83C\uDFA8" },
        { "\uE503", "\uD83C\uDFA9" },
        { "\uE125", "\uD83C\uDFAB" },
        { "\uE324", "\uD83C\uDFAC" },
        { "\uE130", "\uD83C\uDFAF" },
        { "\uE133", "\uD83C\uDFB0" },
        { "\uE42C", "\uD83C\uDFB1" },
        { "\uE03E", "\uD83C\uDFB5" },
        { "\uE326", "\uD83C\uDFB6" },
        { "\uE040", "\uD83C\uDFB7" },
        { "\uE041", "\uD83C\uDFB8" },
        { "\uE042", "\uD83C\uDFBA" },
        { "\uE015", "\uD83C\uDFBE" },
        { "\uE013", "\uD83C\uDFBF" },
        { "\uE42A", "\uD83C\uDFC0" },
        { "\uE132", "\uD83C\uDFC1" },
        { "\uE115", "\uD83C\uDFC3" },
        { "\uE017", "\uD83C\uDFC4" },
        { "\uE131", "\uD83C\uDFC6" },
        { "\uE42B", "\uD83C\uDFC8" },
        { "\uE42D", "\uD83C\uDFCA" },
        { "\uE036", "\uD83C\uDFE0" },
        { "\uE038", "\uD83C\uDFE2" },
        { "\uE153", "\uD83C\uDFE3" },
        { "\uE155", "\uD83C\uDFE5" },
        { "\uE14D", "\uD83C\uDFE6" },
        { "\uE154", "\uD83C\uDFE7" },
        { "\uE158", "\uD83C\uDFE8" },
        { "\uE501", "\uD83C\uDFE9" },
        { "\uE156", "\uD83C\uDFEA" },
        { "\uE157", "\uD83C\uDFEB" },
        { "\uE504", "\uD83C\uDFEC" },
        { "\uE508", "\uD83C\uDFED" },
        { "\uE505", "\uD83C\uDFEF" },
        { "\uE506", "\uD83C\uDFF0" },
        { "\uE52D", "\uD83D\uDC0D" },
        { "\uE134", "\uD83D\uDC0E" },
        { "\uE529", "\uD83D\uDC11" },
        { "\uE528", "\uD83D\uDC12" },
        { "\uE52E", "\uD83D\uDC14" },
        { "\uE52F", "\uD83D\uDC17" },
        { "\uE526", "\uD83D\uDC18" },
        { "\uE10A", "\uD83D\uDC19" },
        { "\uE441", "\uD83D\uDC1A" },
        { "\uE525", "\uD83D\uDC1B" },
        { "\uE019", "\uD83D\uDC1F" },
        { "\uE522", "\uD83D\uDC20" },
        { "\uE523", "\uD83D\uDC24" },
        { "\uE521", "\uD83D\uDC26" },
        { "\uE055", "\uD83D\uDC27" },
        { "\uE527", "\uD83D\uDC28" },
        { "\uE530", "\uD83D\uDC2B" },
        { "\uE520", "\uD83D\uDC2C" },
        { "\uE053", "\uD83D\uDC2D" },
        { "\uE52B", "\uD83D\uDC2E" },
        { "\uE050", "\uD83D\uDC2F" },
        { "\uE52C", "\uD83D\uDC30" },
        { "\uE04F", "\uD83D\uDC31" },
        { "\uE054", "\uD83D\uDC33" },
        { "\uE01A", "\uD83D\uDC34" },
        { "\uE109", "\uD83D\uDC35" },
        { "\uE052", "\uD83D\uDC36" },
        { "\uE10B", "\uD83D\uDC37" },
        { "\uE531", "\uD83D\uDC38" },
        { "\uE524", "\uD83D\uDC39" },
        { "\uE52A", "\uD83D\uDC3A" },
        { "\uE051", "\uD83D\uDC3B" },
        { "\uE419", "\uD83D\uDC40" },
        { "\uE41B", "\uD83D\uDC42" },
        { "\uE41A", "\uD83D\uDC43" },
        { "\uE41C", "\uD83D\uDC44" },
        { "\uE22E", "\uD83D\uDC46" },
        { "\uE22F", "\uD83D\uDC47" },
        { "\uE230", "\uD83D\uDC48" },
        { "\uE231", "\uD83D\uDC49" },
        { "\uE00D", "\uD83D\uDC4A" },
        { "\uE41E", "\uD83D\uDC4B" },
        { "\uE420", "\uD83D\uDC4C" },
        { "\uE00E", "\uD83D\uDC4D" },
        { "\uE421", "\uD83D\uDC4E" },
        { "\uE41F", "\uD83D\uDC4F" },
        { "\uE422", "\uD83D\uDC50" },
        { "\uE10E", "\uD83D\uDC51" },
        { "\uE318", "\uD83D\uDC52" },
        { "\uE302", "\uD83D\uDC54" },
        { "\uE006", "\uD83D\uDC55" },
        { "\uE319", "\uD83D\uDC57" },
        { "\uE321", "\uD83D\uDC58" },
        { "\uE322", "\uD83D\uDC59" },
        { "\uE323", "\uD83D\uDC5C" },
        { "\uE007", "\uD83D\uDC5F" },
        { "\uE13E", "\uD83D\uDC60" },
        { "\uE31A", "\uD83D\uDC61" },
        { "\uE31B", "\uD83D\uDC62" },
        { "\uE536", "\uD83D\uDC63" },
        { "\uE001", "\uD83D\uDC66" },
        { "\uE002", "\uD83D\uDC67" },
        { "\uE004", "\uD83D\uDC68" },
        { "\uE005", "\uD83D\uDC69" },
        { "\uE428", "\uD83D\uDC6B" },
        { "\uE152", "\uD83D\uDC6E" },
        { "\uE429", "\uD83D\uDC6F" },
        { "\uE515", "\uD83D\uDC71" },
        { "\uE516", "\uD83D\uDC72" },
        { "\uE517", "\uD83D\uDC73" },
        { "\uE518", "\uD83D\uDC74" },
        { "\uE519", "\uD83D\uDC75" },
        { "\uE51A", "\uD83D\uDC76" },
        { "\uE51B", "\uD83D\uDC77" },
        { "\uE51C", "\uD83D\uDC78" },
        { "\uE11B", "\uD83D\uDC7B" },
        { "\uE04E", "\uD83D\uDC7C" },
        { "\uE10C", "\uD83D\uDC7D" },
        { "\uE12B", "\uD83D\uDC7E" },
        { "\uE11A", "\uD83D\uDC7F" },
        { "\uE11C", "\uD83D\uDC80" },
        { "\uE253", "\uD83D\uDC81" },
        { "\uE51E", "\uD83D\uDC82" },
        { "\uE51F", "\uD83D\uDC83" },
        { "\uE31C", "\uD83D\uDC84" },
        { "\uE31D", "\uD83D\uDC85" },
        { "\uE31E", "\uD83D\uDC86" },
        { "\uE31F", "\uD83D\uDC87" },
        { "\uE320", "\uD83D\uDC88" },
        { "\uE13B", "\uD83D\uDC89" },
        { "\uE30F", "\uD83D\uDC8A" },
        { "\uE003", "\uD83D\uDC8B" },
        { "\uE034", "\uD83D\uDC8D" },
        { "\uE035", "\uD83D\uDC8E" },
        { "\uE111", "\uD83D\uDC8F" },
        { "\uE306", "\uD83D\uDC90" },
        { "\uE425", "\uD83D\uDC91" },
        { "\uE43D", "\uD83D\uDC92" },
        { "\uE327", "\uD83D\uDC93" },
        { "\uE023", "\uD83D\uDC94" },
        { "\uE328", "\uD83D\uDC97" },
        { "\uE329", "\uD83D\uDC98" },
        { "\uE32A", "\uD83D\uDC99" },
        { "\uE32B", "\uD83D\uDC9A" },
        { "\uE32C", "\uD83D\uDC9B" },
        { "\uE32D", "\uD83D\uDC9C" },
        { "\uE437", "\uD83D\uDC9D" },
        { "\uE204", "\uD83D\uDC9F" },
        { "\uE10F", "\uD83D\uDCA1" },
        { "\uE334", "\uD83D\uDCA2" },
        { "\uE311", "\uD83D\uDCA3" },
        { "\uE13C", "\uD83D\uDCA4" },
        { "\uE331", "\uD83D\uDCA6" },
        { "\uE330", "\uD83D\uDCA8" },
        { "\uE05A", "\uD83D\uDCA9" },
        { "\uE14C", "\uD83D\uDCAA" },
        { "\uE12F", "\uD83D\uDCB0" },
        { "\uE149", "\uD83D\uDCB1" },
        { "\uE14A", "\uD83D\uDCB9" },
        { "\uE11F", "\uD83D\uDCBA" },
        { "\uE00C", "\uD83D\uDCBB" },
        { "\uE11E", "\uD83D\uDCBC" },
        { "\uE316", "\uD83D\uDCBD" },
        { "\uE126", "\uD83D\uDCBF" },
        { "\uE127", "\uD83D\uDCC0" },
        { "\uE148", "\uD83D\uDCD6" },
        { "\uE301", "\uD83D\uDCDD" },
        { "\uE00B", "\uD83D\uDCE0" },
        { "\uE14B", "\uD83D\uDCE1" },
        { "\uE142", "\uD83D\uDCE2" },
        { "\uE317", "\uD83D\uDCE3" },
        { "\uE103", "\uD83D\uDCE9" },
        { "\uE101", "\uD83D\uDCEB" },
        { "\uE102", "\uD83D\uDCEE" },
        { "\uE00A", "\uD83D\uDCF1" },
        { "\uE104", "\uD83D\uDCF2" },
        { "\uE250", "\uD83D\uDCF3" },
        { "\uE251", "\uD83D\uDCF4" },
        { "\uE20B", "\uD83D\uDCF6" },
        { "\uE008", "\uD83D\uDCF7" },
        { "\uE12A", "\uD83D\uDCFA" },
        { "\uE128", "\uD83D\uDCFB" },
        { "\uE129", "\uD83D\uDCFC" },
        { "\uE141", "\uD83D\uDD0A" },
        { "\uE114", "\uD83D\uDD0D" },
        { "\uE03F", "\uD83D\uDD11" },
        { "\uE144", "\uD83D\uDD12" },
        { "\uE145", "\uD83D\uDD13" },
        { "\uE325", "\uD83D\uDD14" },
        { "\uE24C", "\uD83D\uDD1D" },
        { "\uE207", "\uD83D\uDD1E" },
        { "\uE11D", "\uD83D\uDD25" },
        { "\uE116", "\uD83D\uDD28" },
        { "\uE113", "\uD83D\uDD2B" },
        { "\uE23E", "\uD83D\uDD2F" },
        { "\uE209", "\uD83D\uDD30" },
        { "\uE031", "\uD83D\uDD31" },
        { "\uE21A", "\uD83D\uDD32" },
        { "\uE21B", "\uD83D\uDD33" },
        { "\uE219", "\uD83D\uDD34" },
        { "\uE024", "\uD83D\uDD50" },
        { "\uE025", "\uD83D\uDD51" },
        { "\uE026", "\uD83D\uDD52" },
        { "\uE027", "\uD83D\uDD53" },
        { "\uE028", "\uD83D\uDD54" },
        { "\uE029", "\uD83D\uDD55" },
        { "\uE02A", "\uD83D\uDD56" },
        { "\uE02B", "\uD83D\uDD57" },
        { "\uE02C", "\uD83D\uDD58" },
        { "\uE02D", "\uD83D\uDD59" },
        { "\uE02E", "\uD83D\uDD5A" },
        { "\uE02F", "\uD83D\uDD5B" },
        { "\uE03B", "\uD83D\uDDFB" },
        { "\uE509", "\uD83D\uDDFC" },
        { "\uE51D", "\uD83D\uDDFD" },
        { "\uE404", "\uD83D\uDE01" },
        { "\uE412", "\uD83D\uDE02" },
        { "\uE057", "\uD83D\uDE03" },
        { "\uE415", "\uD83D\uDE04" },
        { "\uE405", "\uD83D\uDE09" },
        { "\uE056", "\uD83D\uDE0A" },
        { "\uE40A", "\uD83D\uDE0C" },
        { "\uE106", "\uD83D\uDE0D" },
        { "\uE402", "\uD83D\uDE0F" },
        { "\uE40E", "\uD83D\uDE12" },
        { "\uE108", "\uD83D\uDE13" },
        { "\uE403", "\uD83D\uDE14" },
        { "\uE407", "\uD83D\uDE16" },
        { "\uE418", "\uD83D\uDE18" },
        { "\uE417", "\uD83D\uDE1A" },
        { "\uE105", "\uD83D\uDE1C" },
        { "\uE409", "\uD83D\uDE1D" },
        { "\uE058", "\uD83D\uDE1E" },
        { "\uE059", "\uD83D\uDE20" },
        { "\uE416", "\uD83D\uDE21" },
        { "\uE413", "\uD83D\uDE22" },
        { "\uE406", "\uD83D\uDE23" },
        { "\uE401", "\uD83D\uDE25" },
        { "\uE40B", "\uD83D\uDE28" },
        { "\uE408", "\uD83D\uDE2A" },
        { "\uE411", "\uD83D\uDE2D" },
        { "\uE40F", "\uD83D\uDE30" },
        { "\uE107", "\uD83D\uDE31" },
        { "\uE410", "\uD83D\uDE32" },
        { "\uE40D", "\uD83D\uDE33" },
        { "\uE40C", "\uD83D\uDE37" },
        { "\uE423", "\uD83D\uDE45" },
        { "\uE424", "\uD83D\uDE46" },
        { "\uE426", "\uD83D\uDE47" },
        { "\uE427", "\uD83D\uDE4C" },
        { "\uE41D", "\uD83D\uDE4F" },
        { "\uE10D", "\uD83D\uDE80" },
        { "\uE01E", "\uD83D\uDE83" },
        { "\uE435", "\uD83D\uDE84" },
        { "\uE01F", "\uD83D\uDE85" },
        { "\uE434", "\uD83D\uDE87" },
        { "\uE039", "\uD83D\uDE89" },
        { "\uE159", "\uD83D\uDE8C" },
        { "\uE150", "\uD83D\uDE8F" },
        { "\uE431", "\uD83D\uDE91" },
        { "\uE430", "\uD83D\uDE92" },
        { "\uE432", "\uD83D\uDE93" },
        { "\uE15A", "\uD83D\uDE95" },
        { "\uE01B", "\uD83D\uDE97" },
        { "\uE42E", "\uD83D\uDE99" },
        { "\uE42F", "\uD83D\uDE9A" },
        { "\uE202", "\uD83D\uDEA2" },
        { "\uE135", "\uD83D\uDEA4" },
        { "\uE14E", "\uD83D\uDEA5" },
        { "\uE137", "\uD83D\uDEA7" },
        { "\uE30E", "\uD83D\uDEAC" },
        { "\uE208", "\uD83D\uDEAD" },
        { "\uE136", "\uD83D\uDEB2" },
        { "\uE201", "\uD83D\uDEB6" },
        { "\uE138", "\uD83D\uDEB9" },
        { "\uE139", "\uD83D\uDEBA" },
        { "\uE151", "\uD83D\uDEBB" },
        { "\uE13A", "\uD83D\uDEBC" },
        { "\uE140", "\uD83D\uDEBD" },
        { "\uE309", "\uD83D\uDEBE" },
        { "\uE13F", "\uD83D\uDEC0" },
    };

    private static String convSJIS(byte[] from) throws java.io.UnsupportedEncodingException {
        String str = new String("");

        for (int i = 0; i < from.length; i++) {
            byte[] chr_bytes;
            int chr_code;

            int cur;
            int next;

            chr_bytes = null;
            chr_code = -1;

            cur = from[i] & 0xFF;
            if (from.length > i + 1) {
                next = from[i + 1] & 0xFF;
            } else {
                next = -1;
            }

            if (((cur >= 0x81 && cur <= 0x9f) || (cur >= 0xe0 && cur <= 0xfc)) &&
                    (next >= 0x40 && next <= 0xfc)) {

                chr_bytes = new byte[2];
                chr_bytes[0] = (byte) cur;
                chr_bytes[1] = (byte) next;
                chr_code = ((cur << 8) + next);
                i++;
            } else {
                chr_bytes = new byte[1];
                chr_bytes[0] = (byte) cur;
                chr_code = cur;
            }

            if (chr_code >= 0xf740) {
                String chr_str = SBSJISUnicodeMap.get(chr_code);
                if (chr_str != null) {
                    str += chr_str;
                    chr_code = -1;
                }
            }

            if (chr_code >= 0 && chr_bytes != null) {
                String chr_str = new String(chr_bytes, "Windows-31J");
                str += chr_str;
            }
        }
        return str;
    }

    public static String convSBUnicode(String from) {
        String str = new String(from);

        for (int i = 0; i < SBUnicodeMap.length; i++) {
            str = str.replace(SBUnicodeMap[i][0], SBUnicodeMap[i][1]);
        }

        return str;
    }

    public static void convPduBody(GenericPdu pdu) {

        if (!(pdu instanceof RetrieveConf)) return;

        PduBody body = ((RetrieveConf) pdu).getBody();
        if (body != null) {
            int partsNum = body.getPartsNum();
            for (int i = 0; i < partsNum; i++) {
                PduPart part = body.getPart(i);
                if (LOCAL_LOGV) {
                    Log.v(TAG, "Content-Type: " + new String(part.getContentType()) + "; Charset: " + part.getCharset());
                }
                if (part.getCharset() == CharacterSets.SHIFT_JIS ||
                        (part.getCharset() == 0 && ContentType.TEXT_HTML.equals(new String(part.getContentType())))) {

                    String text;
                    try {
                        text = convSJIS(part.getData());
                    } catch (java.io.UnsupportedEncodingException e) {
                        break;
                    }
                    part.setData(text.getBytes());
                    part.setCharset(CharacterSets.UTF_8);
                } else if (part.getCharset() == 39) {
                    String text;
                    try {
                        text = new String(part.getData(), "iso-2022-jp");
                    } catch (java.io.UnsupportedEncodingException e) {
                        break;
                    }
                    part.setData(text.getBytes());
                    part.setCharset(CharacterSets.UTF_8);
                } else if (part.getCharset() == CharacterSets.UTF_8) {
                    String text = new String(part.getData());
                    String convtext = convSBUnicode(text);
                    part.setData(convtext.getBytes());
                    part.setCharset(CharacterSets.UTF_8);
                } else if ((ContentType.TEXT_HTML.equals(new String(part.getContentType())) ||
                        (ContentType.TEXT_PLAIN.equals(new String(part.getContentType())))) &&
                        part.getCharset() != CharacterSets.UTF_8) {

                    String text;
                    try {
                        text = new String(part.getData(),
                            CharacterSets.getMimeName(part.getCharset()));
                    } catch (java.io.UnsupportedEncodingException e) {
                        break;
                    }
                    part.setData(text.getBytes());
                    part.setCharset(CharacterSets.UTF_8);
                }
            }
        }
    }
}
