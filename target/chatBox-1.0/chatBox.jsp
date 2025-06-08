<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="aichat.models.ProductVariant" %>
<%
    String userMsg = (String) request.getAttribute("userMsg");
    String botReply = (String) request.getAttribute("botReply");
    ProductVariant product = (ProductVariant) request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>ChatBot</title>
<style>
    @keyframes bounce {
        0%, 100% {
            transform: translateY(0);
        }
        50% {
            transform: translateY(-10px);
        }
    }

    @keyframes fadeInUp {
        0% {
            opacity: 0;
            transform: translateY(10px);
        }
        100% {
            opacity: 1;
            transform: translateY(0);
        }
    }

    #chatButton {
        position: fixed;
        bottom: 20px;
        right: 20px;
        background-color: transparent;
        border: none;
        cursor: pointer;
        z-index: 10000;
        animation: bounce 1.5s infinite;
    }

    #chatButton img {
        height: 60px;
        width: 60px;
        border-radius: 50%;
    }

    #chatPopup {
        position: fixed;
        bottom: 90px;
        right: 20px;
        width: 400px;
        height: 600px;
        background-color: #fff;
        border-radius: 12px;
        box-shadow: 0 0 15px rgba(0,0,0,0.15);
        z-index: 9999;
        display: flex;
        flex-direction: column;
        overflow: hidden;
        font-family: Arial, sans-serif;

        opacity: 0;
        transform: translateY(20px);
        pointer-events: none;
        transition: opacity 0.3s ease, transform 0.3s ease;
    }

    #chatPopup.active {
        opacity: 1;
        transform: translateY(0);
        pointer-events: auto;
    }

    .fade-in-up {
        animation: fadeInUp 0.4s ease forwards;
    }

    .header {
        background-color: #EA1D25;
        color: white;
        padding: 10px 15px;
        font-weight: bold;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .header img {
        width: 30px;
        height: 30px;
        margin-right: 10px;
    }

    .header .title {
        display: flex;
        align-items: center;
        gap: 8px;
    }

    #chatbox {
        flex: 1;
        overflow-y: auto;
        padding: 15px;
        font-size: 16px;
        background-color: #fff;
    }

    .user-msg {
        text-align: right;
        margin-bottom: 10px;
    }

    .user-msg b {
        background-color: #e0f7fa;
        color: #000;
        padding: 10px 14px;
        border-radius: 20px 20px 0 20px;
        display: inline-block;
        max-width: 80%;
        word-break: break-word;
    }

    .bot-reply {
        display: flex;
        align-items: flex-start;
        gap: 8px;
        margin-bottom: 10px;
    }

    .bot-reply img.avatar {
        width: 24px;
        height: 24px;
    }

    .bot-reply .msg {
        background-color: #f2f2f2;
        color: #000;
        padding: 10px 14px;
        border-radius: 20px 20px 20px 0;
        max-width: 80%;
        word-break: break-word;
    }

    form#chatForm {
        display: flex;
        align-items: center;
        border-top: 1px solid #ccc;
        padding: 8px;
        background-color: #fafafa;
    }

    #userInput {
        flex: 1;
        padding: 10px;
        font-size: 16px;
        border: 1px solid #ccc;
        border-radius: 18px;
        outline: none;
    }

    #sendBtn {
        background-color: transparent;
        border: none;
        font-size: 22px;
        margin-left: 10px;
        color: #2196f3;
        cursor: pointer;
    }

    .product-card {
        border: 1px solid #ccc;
        border-radius: 8px;
        padding: 10px;
        margin-top: 5px;
        text-align: center;
        background: white;
    }

    .product-card img {
        max-width: 100%;
        height: auto;
        border-radius: 5px;
    }

    .add-to-cart-btn {
        margin-top: 5px;
        background-color: #28a745;
        color: white;
        border: none;
        padding: 8px 12px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
    }

    .add-to-cart-btn:hover {
        background-color: #218838;
    }
</style>

<script>
    function toggleChat() {
        const popup = document.getElementById('chatPopup');
        const chatbox = document.getElementById('chatbox');

        if (!popup.classList.contains('active')) {
            popup.classList.add('active');
            document.getElementById('userInput').focus();

            // Trigger animation for chatbox
            chatbox.classList.remove('fade-in-up'); // reset
            void chatbox.offsetWidth; // force reflow
            chatbox.classList.add('fade-in-up');
        } else {
            popup.classList.remove('active');
        }
    }
</script>

    </head>
    <body>

        <!-- Nút mở chat -->
        <button id="chatButton" onclick="toggleChat()">
            <img src="https://is1-ssl.mzstatic.com/image/thumb/Purple221/v4/2d/49/b6/2d49b636-076d-734d-a78c-b52f5c2c3c0d/AppIcon-0-0-1x_U007epad-0-11-0-85-220.png/246x0w.webp"  />
        </button>

        <!-- Khung chat -->
        <div id="chatPopup">
            <!-- Header -->
            <div class="header">
                <div class="title">
                    <img src="https://cdn-icons-png.flaticon.com/512/4712/4712109.png" alt="bot" />
                    THEGIOICONGNGHE.COM
                </div>
                <div onclick="toggleChat()" style="cursor:pointer;font-size:20px;">–</div>
            </div>

            <!-- Nội dung chat -->
            <div id="chatbox">
                <% if (userMsg != null) {%>
                <p class="user-msg"><b><%= userMsg%></b></p>
                        <% } %>

                <% if (botReply != null) {%>
                <div class="bot-reply">
                    <img class="avatar" src="https://cdn-icons-png.flaticon.com/512/4712/4712109.png" />
                    <div class="msg"><b>Bot:</b> <%= botReply%></div>
                </div>
                <% } %>

                <% if (product != null) {%>
                <div class="bot-reply">
                    <img class="avatar" src="https://cdn-icons-png.flaticon.com/512/4712/4712109.png" />
                    <div class="msg">
                        <div class="product-card">
                            <img src="<%= product.getImageUrls()%>" alt="<%= product.getColor()%>" />
                            <p><strong><%= product.getPrice()%></strong></p>
                            <p>Giá: <%= product.getPrice()%></p>
                            <p>ROM: <%= product.getRom()%></p>
                            <button class="add-to-cart-btn" onclick="alert('Đã thêm sản phẩm <%= product.getVariantId()%> vào giỏ hàng!')">🛒 Thêm vào giỏ</button>
                        </div>
                    </div>
                </div>
                <% }%>
            </div>

            <!-- Nhập tin nhắn -->
            <form id="chatForm" action="chat" method="post">
                <input type="text" id="userInput" name="message" placeholder="Nhập tin nhắn..." autocomplete="off" required />
                <button type="submit" id="sendBtn">📨</button>
            </form>
        </div>

    </body>
</html>
