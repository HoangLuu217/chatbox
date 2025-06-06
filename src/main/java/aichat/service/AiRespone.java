/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aichat.service;

import aichat.models.AITraining;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class AiRespone {

    private final LinkedList<String> queue = new LinkedList<>();
    AITrainingService aITrainingService = new AITrainingService();
    geminiService chat = new geminiService();

//    public String isRelatedToName(String promt) {
//        List<String> phoneName = aITrainingService.getAllProductNames();
//        // System.out.println(phoneName.toString());
//        String answer = null;
//
//        try {
//            answer = chat.callGeminiAPI("Câu hỏi này có liên quan đến tên của điện thoại nào trong list " + phoneName.toString().toLowerCase() + " hay không, NẾU KHÔNG ĐỀ CẬP ĐẾN THÌ TRẢ LỜI LÀ KHÔNG, nếu có thì đưa ra chỉ tên có trong list(ví dụ :iphone 15), chỉ 1 đáp án, phải trả lời chính xác." + promt);
//
//        } catch (Exception ex) {
//            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return answer;
//    }
    public String isRelatedToName(String promt) {
        List<String> phoneNames = aITrainingService.getAllProductNames();
        String answer = null;

        // Chuẩn hóa prompt lowercase để check contain
        String normalizedPrompt = promt.toLowerCase();

        // Bước 1: Check contain thủ công để ưu tiên trả lời nhanh, chính xác nếu có trùng
        for (String name : phoneNames) {
            if (normalizedPrompt.contains(name.toLowerCase())) {
                return name;  // Trả về tên đúng trong list ngay lập tức
            }
        }

        // Bước 2: Nếu không tìm thấy, dùng AI để phân tích
        try {
            String aiPrompt = "Câu hỏi này có liên quan đến tên của điện thoại nào trong list "
                    + phoneNames.toString().toLowerCase()
                    + " hay không, NẾU KHÔNG ĐỀ CẬP ĐẾN THÌ TRẢ LỜI LÀ KHÔNG, nếu có thì đưa ra chỉ tên có trong list (ví dụ: iphone 15), chỉ 1 đáp án, phải trả lời chính xác."
                    + promt;

            answer = chat.callGeminiAPI(aiPrompt);

            // Chuẩn hóa kết quả AI, trả về "không" nếu AI không nhận diện được
            if (answer == null || answer.trim().isEmpty() || answer.trim().equalsIgnoreCase("không")) {
                return "không";
            }

            // Nếu kết quả AI trả về trùng với list thì trả lại, nếu không thì "không"
            for (String name : phoneNames) {
                if (answer.toLowerCase().contains(name.toLowerCase())) {
                    return name;
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "không";
    }

    public String isRelatedToColor(String promt) {
        List<String> color = aITrainingService.getAllColors();
        String answer = null;
        try {
            answer = chat.callGeminiAPI("Câu hỏi này có liên quan đến màu của điện thoại nào trong list " + color.toString().toLowerCase() + " hay không,  NẾU KHÔNG ĐỀ CẬP ĐẾN THÌ TRẢ LỜI LÀ KHÔNG,nếu có thì đưa ra chỉ màu có trong list (ví dụ :đỏ), nếu là tiếng anh thì dịch sang tiếng việt và so sánh, chỉ đưa ra 1 đáp án màu thôi không trả lời gì thêm , phải trả lời chính xác." + promt);
        } catch (Exception ex) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answer;
    }

    public String isRelatedToBrandNames(String promt) {
        List<String> BrandNames = aITrainingService.getAllBrandNames();
        String answer = null;
        try {
            answer = chat.callGeminiAPI("Câu hỏi này có liên quan đến hãng điện thoại nào trong list " + BrandNames.toString().toLowerCase() + " hay không, NẾU KHÔNG ĐỀ CẬP ĐẾN THÌ TRẢ LỜI LÀ KHÔNG, nếu có thì đưa ra chỉ tên hãng có trong list (ví dụ :iphone) chỉ đưa ra 1 đáp án hãng thôi không trả lời gì thêm , phải trả lời chính xác." + promt);
        } catch (Exception ex) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
        }
        return answer;
    }

//    public String isRelatedToRamValues(String promt) {
//        List<String> RamValues = aITrainingService.getAllRamValues();
//        String answer = null;
//
//        try {
//            answer = chat.callGeminiAPI("Câu hỏi này có liên quan đến dung lượng Ram nào trong list " + RamValues.toString().toLowerCase() + " hay không, NẾU KHÔNG ĐỀ CẬP ĐẾN THÌ TRẢ LỜI LÀ KHÔNG, nếu có thì đưa ra chỉ dung lượng Ram có trong list (ví dụ :8gb) chỉ đưa ra 1 đáp án như vậy thôi không trả lời gì thêm ,  phải trả lời chính xác." + promt);
//        } catch (Exception ex) {
//            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return answer;
//    } 
    public String isRelatedToRamValues(String promt) {
        List<String> ramValues = aITrainingService.getAllRamValues(); // ví dụ: ["4gb", "6gb", "8gb", "12gb"]
        String normalizedPrompt = promt.toLowerCase().replaceAll("[^a-z0-9 ]", " "); // Bỏ ký tự đặc biệt

        // Ưu tiên kiểm tra bằng tay trước
        if (normalizedPrompt.contains("ram")) {
            for (String ram : ramValues) {
                if (normalizedPrompt.contains(ram.toLowerCase())) {
                    return ram;
                }
            }
        }

        // Nếu không match được tay, fallback sang AI
        String answer = null;
        try {
            answer = chat.callGeminiAPI(
                    "Câu hỏi này có liên quan đến dung lượng RAM nào trong list " + ramValues.toString().toLowerCase()
                    + " hay không? NẾU KHÔNG ĐỀ CẬP ĐẾN THÌ TRẢ LỜI LÀ KHÔNG. Nếu có thì đưa ra chính xác 1 dung lượng RAM có trong list (ví dụ: 8gb), KHÔNG được trả lời gì thêm ngoài dung lượng RAM, phải chính xác." + promt
            );
        } catch (Exception ex) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
        }

        return answer != null ? answer : "không";
    }

//    public String isRelatedToRomValues(String promt) {
//        List<String> RomValues = aITrainingService.getAllRomValues();
//        String answer = null;
//
//        try {
//            answer = chat.callGeminiAPI("Câu hỏi này có liên quan đến dung lượng ROM nào trong list " + RomValues.toString().toLowerCase() + " hay không, NẾU KHÔNG ĐỀ CẬP ĐẾN THÌ TRẢ LỜI LÀ không, nếu có thì đưa ra chỉ dung lượng ROM có trong list (ví dụ :64gb) chỉ đưa ra 1 đáp án như vậy thôi không trả lời gì thêm , phải trả lời chính xác." + promt);
//        } catch (Exception ex) {
//            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return answer;
//    }
    public String isRelatedToRomValues(String promt) {
        List<String> romValues = aITrainingService.getAllRomValues(); // ví dụ: ["32gb", "64gb", "128gb"]
        String normalizedPrompt = promt.toLowerCase().replaceAll("[^a-z0-9]", ""); // Xoá ký tự đặc biệt, ghép số và chữ

        // Ưu tiên tìm trong list trước
        for (String rom : romValues) {
            if (normalizedPrompt.contains(rom.toLowerCase().replaceAll("\\s+", ""))) {
                return rom;
            }
        }

        // Nếu không tìm thấy → gọi AI
        String answer = null;
        try {
            answer = chat.callGeminiAPI(
                    "Câu hỏi này có liên quan đến dung lượng ROM nào trong list " + romValues.toString().toLowerCase()
                    + " hay không? NẾU KHÔNG ĐỀ CẬP ĐẾN THÌ TRẢ LỜI LÀ không. Nếu có thì đưa ra chính xác 1 dung lượng ROM có trong list (ví dụ: 128gb), KHÔNG được trả lời gì thêm ngoài dung lượng ROM." + promt
            );
        } catch (Exception ex) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
        }

        return answer != null ? answer : "không";
    }

//    public String isRelatedToPrice(String promt) {
//        List<String> Price = aITrainingService.getAllPrices();
//        String answer = null;
//
//        try {
//            // Bước 1: Phân tích yêu cầu giá: lớn hơn, nhỏ hơn, bằng hoặc không liên quan
//            String condition = chat.callGeminiAPI(
//                    "Phân tích câu hỏi sau để xác định yêu cầu về giá tiền: " + promt
//                    + "\nNếu có yêu cầu là giá 'bằng' hoặc 'nhỏ hơn hoặc bằng' thì trả lời 'bằng hoặc bé hơn'."
//                    + "\nNếu là giá lớn hơn thì trả lời 'lớn hơn'."
//                    + "\nNếu không có yêu cầu về giá tiền hoặc không liên quan thì trả lời 'không'."
//                    + "\nChỉ trả lời một trong các từ: 'bằng hoặc bé hơn', 'lớn hơn', hoặc 'không'.");
//
//            if (condition.equalsIgnoreCase("không")) {
//                return "không";
//            }
//
//            // Bước 2: Yêu cầu trả lời số tiền trong list hoặc số tiền gần nhất theo điều kiện
//            String promptPrice = "Danh sách các mức giá: " + Price.toString().toLowerCase() + ".\n"
//                    + "Câu hỏi: " + promt + "\n"
//                    + "Dựa trên điều kiện '" + condition + "', chỉ trả lời đúng 1 số tiền có trong danh sách hoặc số tiền gần nhất theo điều kiện đó."
//                    + " Nếu câu hỏi không liên quan đến giá thì trả lời 'không'.\n"
//                    + "KHÔNG ĐƯỢC TRẢ LỜI GÌ THÊM, chỉ trả lời số tiền (ví dụ: 12000000) hoặc 'không'.";
//
//            answer = chat.callGeminiAPI(promptPrice);
//
//            if (answer == null || answer.trim().isEmpty()) {
//                return "không";
//            }
//            return answer.trim().toLowerCase();
//
//        } catch (Exception ex) {
//            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "không";
//    }
    public static String normalizePriceFromText(String text) {
        text = text.toLowerCase().replaceAll("[^0-9kmtỷtriệu]", ""); // chỉ giữ ký tự số và đơn vị
        text = text.replaceAll("k", "000");
        text = text.replaceAll("trieu|triệu|tr", "000000");
        text = text.replaceAll("ty|tỷ", "000000000");

        // Sau khi thay, nếu còn lại toàn số, thì parse ra
        if (text.matches("\\d+")) {
            return text;
        }

        return ""; // không xác định được
    }

    public String isRelatedToPrice(String promt) {
        List<String> priceList = aITrainingService.getAllPrices(); // ví dụ ["5000000", "8000000", "12000000"]
        String normalizedPrompt = promt.toLowerCase().replaceAll("[^0-9]", ""); // Lấy chuỗi chỉ có số để match thủ công
        normalizedPrompt = normalizePriceFromText(promt);
        String answer = null;

        // 1. Check thủ công có chứa giá trong list không
        for (String price : priceList) {
            if (normalizedPrompt.contains(price)) {
                return price;
            }
        }

        try {
            // 2. Phân tích yêu cầu: lớn hơn / bé hơn / bằng
            String condition = chat.callGeminiAPI(
                    "Phân tích câu hỏi sau để xác định yêu cầu về giá tiền: " + promt
                    + "\nNếu có yêu cầu là giá 'bằng' hoặc 'nhỏ hơn hoặc bằng' thì trả lời 'bằng hoặc bé hơn'."
                    + "\nNếu là giá lớn hơn thì trả lời 'lớn hơn'."
                    + "\nNếu không có yêu cầu về giá tiền hoặc không liên quan thì trả lời 'không'."
                    + "\nChỉ trả lời một trong các từ: 'bằng hoặc bé hơn', 'lớn hơn', hoặc 'không'.");

            if (condition.equalsIgnoreCase("không")) {
                return "không";
            }

            // 3. Gửi lại prompt để AI chọn số tiền phù hợp nhất theo điều kiện
            String promptPrice = "Danh sách các mức giá: " + priceList.toString().toLowerCase() + ".\n"
                    + "Câu hỏi: " + promt + "\n"
                    + "Dựa trên điều kiện '" + condition + "', chỉ trả lời đúng 1 số tiền có trong danh sách hoặc số tiền gần nhất theo điều kiện đó."
                    + " Nếu câu hỏi không liên quan đến giá thì trả lời 'không'.\n"
                    + "KHÔNG ĐƯỢC TRẢ LỜI GÌ THÊM, chỉ trả lời số tiền (ví dụ: 12000000) hoặc 'không'.";

            answer = chat.callGeminiAPI(promptPrice);

            if (answer == null || answer.trim().isEmpty()) {    
                return "không";
            }
            return answer.trim().toLowerCase();

        } catch (Exception ex) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "không";
    }

    public LinkedList<String> saveRecentQuestion(String promt) {
        int maxSize = 3;
        if (queue.size() >= maxSize) {
            queue.removeFirst(); // Xóa phần tử đầu tiên (FIFO)
        }
        queue.addLast(promt); // Thêm vào cuối
        return queue;
    }

    public void answer(String ask) {
        System.out.println("Ten Iphone " + isRelatedToName(ask));
        System.out.println("ten hang " + isRelatedToBrandNames(ask));
        System.out.println("mau " + isRelatedToColor(ask));
        System.out.println("RAM " + isRelatedToRamValues(ask));
        System.out.println("ROM " + isRelatedToRomValues(ask));
        System.out.println("gia " + isRelatedToPrice(ask));
    }

    public AITraining checkanswer(String promt) {
        boolean hasName;
        boolean hasBrand;
        boolean hasColor;
        boolean hasRam;
        boolean hasRom;
        boolean hasGia;
        if (!"không".equalsIgnoreCase(isRelatedToName(promt))) {
            hasName = true;
        }
        if (!"không".equalsIgnoreCase(isRelatedToBrandNames(promt))) {
            hasBrand = true;
        }
        if (!"không".equalsIgnoreCase(isRelatedToColor(promt))) {
            hasColor = true;
        }
        if (!"không".equalsIgnoreCase(isRelatedToRamValues(promt))) {
            hasRam = true;
        }
        if (!"không".equalsIgnoreCase(isRelatedToRomValues(promt))) {
            hasRom = true;
        }
        if (!"không".equalsIgnoreCase(isRelatedToPrice(promt))) {
            hasName = true;
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AiRespone ai = new AiRespone();
        while (true) {
            System.out.print("ban muon mua gi (go 'exit' de thoat): ");
            String input = scanner.nextLine();
            String ask = ai.saveRecentQuestion(input).toString();
            System.out.println(ask);
            ai.answer(ask);
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Tạm biệt nhé!");
                break;
            }

            //System.out.println(ask);
        }
    }
}
