/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aichat.service;

import aichat.models.AITraining;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Admin
 */
public class AiRespone {

    private final LinkedList<String> queue = new LinkedList<>();
    AITrainingService aITrainingService = new AITrainingService();
    geminiService chat = new geminiService();
    List<AITraining> infor = aITrainingService.getAllTrainings();

    public String isRelatedToName(String prompt) {
        List<String> phoneNames = aITrainingService.getAllProductNames(); // Ví dụ: ["iPhone 15", "Samsung Galaxy S23", "Xiaomi Redmi Note 12"]
        String answer = null;

        // Chuẩn hóa prompt thành chữ thường để so sánh
        String normalizedPrompt = prompt.toLowerCase();

        // Bước 1: Check thủ công xem prompt có chứa tên trong list không để trả lời nhanh
        for (String name : phoneNames) {
            if (normalizedPrompt.contains(name.toLowerCase())) {
                return name; // Trả về tên chính xác trong list
            }
        }

        // Bước 2: Nếu không tìm thấy, gọi AI phân tích
        try {
            String aiPrompt = "Danh sách tên điện thoại: " + phoneNames.toString().toLowerCase() + ".\n"
                    + "Câu hỏi: \"" + prompt + "\"\n"
                    + "Chỉ trả lời tên điện thoại chính xác có trong danh sách, KHÔNG dựa vào các con số hoặc giá tiền trong câu hỏi.\n"
                    + "Nếu không có tên điện thoại nào liên quan thì trả lời 'không'.\n"
                    + "KHÔNG trả lời gì khác ngoài tên hoặc 'không'.\n"
                    + "\nVí dụ:\n"
                    + "- Câu hỏi: \"Bạn có iPhone 15 không?\" → Trả lời: iphone 15\n"
                    + "- Câu hỏi: \"Điện thoại Samsung nào mới nhất?\" → Trả lời: samsung galaxy s23\n"
                    + "- Câu hỏi: \"Tôi hỏi về điện thoại tầm 15 triệu\" → Trả lời: không\n"
                    + "- Câu hỏi: \"Có pin trâu không?\" → Trả lời: không";

            answer = chat.callGeminiAPI(aiPrompt);

            if (answer == null || answer.trim().isEmpty() || answer.trim().equalsIgnoreCase("không")) {
                return "không";
            }

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

    public String isRelatedToColor(String prompt) {
        List<String> colors = aITrainingService.getAllColors(); // ví dụ: ["đen", "trắng", "đỏ", "vàng", "xanh", "tím"]
        String answer = null;

        try {
            String aiPrompt = "Danh sách các màu điện thoại: " + colors.toString().toLowerCase() + ".\n"
                    + "Câu hỏi: \"" + prompt + "\"\n"
                    + "Yêu cầu:\n"
                    + "- Nếu câu hỏi có nhắc đến màu sắc thuộc danh sách trên, hãy trả lời chính xác 1 tên màu tiếng Việt (ví dụ: đỏ).\n"
                    + "- Nếu màu được viết bằng tiếng Anh (ví dụ: red), hãy dịch sang tiếng Việt rồi so sánh.\n"
                    + "- Nếu không đề cập đến màu sắc, trả lời đúng 1 từ: \"không\".\n"
                    + "- KHÔNG được giải thích thêm, KHÔNG viết gì ngoài tên màu hoặc \"không\".\n"
                    + "\nVí dụ:\n"
                    + "- Câu hỏi: \"Có màu đỏ không?\" → Trả lời: đỏ\n"
                    + "- Câu hỏi: \"I like the blue one\" → Trả lời: xanh\n"
                    + "- Câu hỏi: \"Có điện thoại nào pin trâu không?\" → Trả lời: không";

            answer = chat.callGeminiAPI(aiPrompt);
            if (answer == null || answer.trim().isEmpty()) {
                return "không";
            }
            return answer.trim().toLowerCase();

        } catch (Exception ex) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
            return "không";
        }
    }

    public String isRelatedToBrandNames(String prompt) {
        List<String> brandNames = aITrainingService.getAllBrandNames(); // ví dụ: ["iphone", "samsung", "oppo", "xiaomi"]
        String answer = null;

        try {
            String aiPrompt = "Danh sách các hãng điện thoại: " + brandNames.toString().toLowerCase() + ".\n"
                    + "Câu hỏi: \"" + prompt + "\"\n"
                    + "Nếu trong câu hỏi có đề cập đến một hãng điện thoại nào đó trong danh sách trên, hãy trả lời chính xác tên hãng đó (ví dụ: iphone).\n"
                    + "Nếu không đề cập đến hãng nào, trả lời đúng 1 từ duy nhất: \"không\".\n"
                    + "Chỉ trả lời duy nhất tên hãng hoặc \"không\" — không được giải thích thêm.\n"
                    + "Ví dụ:\n"
                    + "- Câu hỏi: \"Tôi nên mua iPhone nào?\" → Trả lời: apple\n"
                    + "- Câu hỏi: \"Điện thoại nào chụp hình đẹp?\" → Trả lời: không";

            answer = chat.callGeminiAPI(aiPrompt);
            if (answer == null || answer.trim().isEmpty()) {
                return "không";
            }
            return answer.trim().toLowerCase();

        } catch (Exception ex) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
            return "không";
        }
    }

    public String isRelatedToRomValues(String prompt) {
        List<Integer> romValues = aITrainingService.getAllRomValues(); // ví dụ: [32, 64, 128, 256]
        String normalized = prompt.toLowerCase().replaceAll("[^a-z0-9]", ""); // ví dụ: "tôi muốn 128 gb" → "toimuon128gb"

        // Tìm trực tiếp
        for (Integer rom : romValues) {
            if (normalized.contains(rom + "gb") || normalized.contains(rom.toString())) {
                return rom + "gb";
            }
        }

        // Nếu không có thì hỏi AI
        String promptToSend = "Câu hỏi sau có đề cập đến dung lượng ROM cụ thể nào trong danh sách sau không: "
                + romValues.stream().map(v -> v + "gb").toList() + "?\n"
                + "Chỉ trả lời duy nhất 1 dung lượng nếu có. Không thì trả lời 'không'.\n"
                + "KHÔNG được đoán dựa trên kiến thức sản phẩm, chỉ dựa trên câu hỏi thôi.\n"
                + "Câu: " + prompt;

        try {
            String answer = chat.callGeminiAPI(promptToSend);
            if (answer == null || answer.trim().isEmpty()) {
                return "không";
            }

            String cleaned = answer.trim().toLowerCase().replaceAll("[^0-9]", "");
            for (Integer rom : romValues) {
                if (cleaned.equals(rom.toString())) {
                    return rom + "gb";
                }
            }

        } catch (Exception e) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, e);
        }

        return "không";
    }

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

    public BigDecimal isRelatedToPrice(String prompt) {
        List<BigDecimal> priceList = aITrainingService.getAllPrices(); // Ví dụ: [5000000, 8000000, 12000000]

        try {
            // B1: Xác định yêu cầu về giá (lớn hơn, bé hơn, hoặc không liên quan)
            String condition = chat.callGeminiAPI(
                    "Phân tích câu hỏi sau để xác định yêu cầu về giá tiền: " + prompt
                    + "\nNếu có yêu cầu là giá 'bằng' hoặc 'nhỏ hơn hoặc bằng' thì trả lời 'bằng hoặc bé hơn'."
                    + "\nNếu là giá lớn hơn thì trả lời 'lớn hơn'."
                    + "\nNếu không có yêu cầu về giá tiền hoặc không liên quan thì trả lời 'không'."
                    + "\nChỉ trả lời một trong các từ: 'bằng hoặc bé hơn', 'lớn hơn', hoặc 'không'."
            ).trim().toLowerCase();

            if (condition.equals("không")) {
                return null;
            }

            // B2: Yêu cầu AI chọn giá phù hợp trong danh sách
            String promptPrice = "Danh sách các mức giá: " + priceList.toString() + ".\n"
                    + "Câu hỏi: " + prompt + "\n"
                    + "Dựa trên điều kiện '" + condition + "', chỉ trả lời đúng 1 số tiền có trong danh sách hoặc số tiền gần nhất theo điều kiện đó."
                    + " Nếu câu hỏi không liên quan đến giá thì trả lời 'không'."
                    + "\nKHÔNG ĐƯỢC TRẢ LỜI GÌ THÊM, chỉ trả lời số tiền (ví dụ: 12000000) hoặc 'không'.";

            String answer = chat.callGeminiAPI(promptPrice);
            if (answer == null || answer.trim().equalsIgnoreCase("không")) {
                return null;
            }

            // B3: Parse kết quả AI trả về thành BigDecimal
            try {
                return new BigDecimal(answer.trim());
            } catch (NumberFormatException e) {
                return null; // Nếu AI trả ra kết quả không hợp lệ
            }

        } catch (Exception ex) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public HashMap<String, List<AITraining>> getProductRecommendation(String prompt) {
        HashMap<String, List<AITraining>> result = new HashMap<>();
        List<AITraining> allProducts = aITrainingService.getAllTrainings();

        // Phân tích các tiêu chí từ câu hỏi
        String brand = isRelatedToBrandNames(prompt);
        String name = isRelatedToName(prompt);
        String color = isRelatedToColor(prompt);
        String rom = isRelatedToRomValues(prompt);
        BigDecimal price = isRelatedToPrice(prompt);

        // Lọc sản phẩm theo các tiêu chí
        List<AITraining> matchingProducts = allProducts.stream()
                .filter(p -> brand.equals("không") || p.getBrandName().toLowerCase().contains(brand))
                .filter(p -> name.equals("không") || p.getProductName().toLowerCase().contains(name))
                .filter(p -> color.equals("không") || p.getColor().toLowerCase().contains(color))
                .filter(p -> rom.equals("không") || p.getRom() != null && p.getRom().toString().contains(rom.replace("gb", "")))
                .filter(p -> price == null || p.getPrice().compareTo(price) <= 0)
                .collect(Collectors.toList());

        // Tạo câu tư vấn bằng AI
        StringBuilder aiPrompt = new StringBuilder();
        aiPrompt.append("Dựa trên thông tin sau:\n");
        aiPrompt.append("- Tiêu chí tìm kiếm: ");
        if (!brand.equals("không")) {
            aiPrompt.append("hãng ").append(brand).append(", ");
        }
        if (!name.equals("không")) {
            aiPrompt.append("mẫu ").append(name).append(", ");
        }
        if (!color.equals("không")) {
            aiPrompt.append("màu ").append(color).append(", ");
        }
        if (!rom.equals("không")) {
            aiPrompt.append("dung lượng ").append(rom).append(", ");
        }
        if (price != null) {
            aiPrompt.append("giá tầm ").append(price).append("đ, ");
        }
        aiPrompt.append("\n");

        aiPrompt.append("- Sản phẩm có sẵn: ");
        for (AITraining p : matchingProducts) {
            aiPrompt.append(p.getProductName())
                    .append(" màu ").append(p.getColor())
                    .append(" ").append(p.getRom()).append("GB")
                    .append(" giá ").append(p.getPrice()).append("đ, ");
        }
        aiPrompt.append("\n");

        aiPrompt.append("Hãy tư vấn cho khách hàng một cách thân thiện, nếu không có sản phẩm đúng yêu cầu thì đề xuất sản phẩm tương tự. ");
        aiPrompt.append("Nếu có sản phẩm phù hợp thì giới thiệu chi tiết. ");
        aiPrompt.append("Trả lời ngắn gọn, tự nhiên như đang nói chuyện.");

        try {
            String aiResponse = chat.callGeminiAPI(aiPrompt.toString());
            result.put(aiResponse, matchingProducts);
        } catch (Exception ex) {
            Logger.getLogger(AiRespone.class.getName()).log(Level.SEVERE, null, ex);
            result.put("Xin lỗi, tôi không thể tư vấn lúc này. Vui lòng thử lại sau.", new ArrayList<>());
        }

        return result;
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
        System.out.println("ROM " + isRelatedToRomValues(ask));
        System.out.println("gia " + isRelatedToPrice(ask));
    }

    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        AiRespone ai = new AiRespone();
//        while (true) {
//            System.out.print("ban muon mua gi (go 'exit' de thoat): ");
//            String input = scanner.nextLine();
//            String ask = ai.saveRecentQuestion(input).toString();
//            System.out.println(ask);
//            //ai.checkanswer(ask);
//            ai.answer(ask);
//            if (input.equalsIgnoreCase("exit")) {
//                System.out.println("Tạm biệt nhé!");
//                break;
//            }
//        }

        Scanner scanner = new Scanner(System.in);
        AiRespone ai = new AiRespone();

        System.out.println("=== Chuong trinh tu van san pham ===");
        System.out.println("Nhap 'exit' de thoat");
        System.out.println("-----------------------------------");

        while (true) {
            System.out.print("\nBan muon tim san pham gi? ");
            String input = scanner.nextLine();
            String ask = ai.saveRecentQuestion(input).toString();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Tam biet nhe!");
                break;
            }

            // Xu ly cau hoi va hien thi ket qua
            HashMap<String, List<AITraining>> result = ai.getProductRecommendation(ask);
            System.out.println("\nTu van: " + result.keySet().iterator().next());
            System.out.println("\nSan pham phu hop:");
            result.values().iterator().next().forEach(p
                    -> System.out.println("- " + p.getProductName() + " | Mau: " + p.getColor() + " | ROM: " + p.getRom() + "GB | Gia: " + p.getPrice() + "d")
            );
        }

        scanner.close();
    }
}
