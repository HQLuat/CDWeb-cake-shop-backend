-- ==================== SẢN PHẨM 1 ====================
INSERT IGNORE INTO products (name, price, description, detail_description, storage_guide, collection, shipping_info, ingredients, fresh_guarantee)
VALUES (
           'Red Velvet Muse Cake',
           45.00,
           'Chiếc bánh Red Velvet đặc trưng với kết cấu mềm mịn như nhung, kết hợp lớp kem cheese tươi mát.',
           'Mỗi chiếc bánh Red Velvet Muse là một tác phẩm nghệ thuật thủ công. Chúng tôi sử dụng phương pháp trộn bột truyền thống, màu đỏ chiết xuất từ củ dền hữu cơ.',
           'Bảo quản trong ngăn mát tủ lạnh, dùng trong vòng 3 ngày.',
           'Signature Collection',
           'Giao hàng nhanh trong 2h tại nội thành',
           'Bơ hữu cơ AOP,Bột mì thượng hạng,Phô mai tươi,Trứng gà thả vườn',
           true
       );
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (1, 'https://images.unsplash.com/photo-1586788224331-947f68671cf1?w=800', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (1, 'https://images.unsplash.com/photo-1562440499-64c9a111f713?w=800', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (1, 'https://images.unsplash.com/photo-1571115177098-24ec42ed204d?w=800', 2);
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (1, 'Nguyễn Thị Lan', 5, 'Bánh ngon tuyệt vời, kem cheese rất mịn!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (1, 'Trần Văn Minh', 5, 'Giao hàng nhanh, bánh còn tươi, sẽ ủng hộ tiếp!', NOW());

-- ==================== SẢN PHẨM 2 ====================
INSERT IGNORE INTO products (name, price, description, detail_description, storage_guide, collection, shipping_info, ingredients, fresh_guarantee)
VALUES (
           'Chocolate Fudge Truffle Cheesecake',
           52.00,
           'Cheesecake socola đậm đà với lớp truffle tan chảy, phủ ganache bóng mượt.',
           'Được làm từ socola Bỉ nguyên chất 70% cacao, kết hợp phô mai Philadelphia cao cấp. Mỗi lớp được xếp tỉ mỉ để tạo nên hương vị layered hoàn hảo.',
           'Bảo quản ngăn mát 0-4°C, dùng trong 4 ngày. Không để đông đá.',
           'Premium Collection',
           'Giao hàng nhanh trong 2h tại nội thành',
           'Socola Bỉ 70%,Phô mai Philadelphia,Kem tươi,Bơ nhạt,Đường nâu',
           true
       );
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (2, 'https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=800', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (2, 'https://images.unsplash.com/photo-1606313564200-e75d5e30ef07?w=800', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (2, 'https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=800', 2);
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (2, 'Lê Thị Hoa', 5, 'Socola đậm đà, cheesecake không bị ngấy. Cực phẩm!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (2, 'Phạm Quốc Bảo', 4, 'Ngon lắm, lần sau sẽ order thêm!', NOW());

-- ==================== SẢN PHẨM 3 ====================
INSERT IGNORE INTO products (name, price, description, detail_description, storage_guide, collection, shipping_info, ingredients, fresh_guarantee)
VALUES (
           'Signature Sourdough Bread',
           12.00,
           'Bánh mì sourdough lên men 72 giờ với vỏ ngoài giòn tan, ruột dai mềm và hương vị phức tạp.',
           'Bánh mì sourdough được ủ với men tự nhiên truyền thống qua 72 giờ lên men chậm. Vỏ bánh được nướng ở nhiệt độ cao tạo nên lớp vỏ giòn đặc trưng, ruột bánh có cấu trúc lỗ hổng lớn đẹp mắt.',
           'Bảo quản ở nhiệt độ phòng trong túi vải, dùng trong 2 ngày. Có thể hâm nóng lò 180°C trong 5 phút.',
           'Artisan Bread Collection',
           'Giao hàng nhanh trong 2h tại nội thành',
           'Bột mì hữu cơ,Men tự nhiên sourdough,Muối biển,Nước lọc',
           true
       );
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (3, 'https://images.unsplash.com/photo-1585478259715-4d3a04c8a398?w=800', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (3, 'https://images.unsplash.com/photo-1509440159596-0249088772ff?w=800', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (3, 'https://images.unsplash.com/photo-1549931319-a545dcf3bc7b?w=800', 2);
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (3, 'Nguyễn Văn An', 5, 'Vỏ giòn, ruột dai, hương vị lên men rất tự nhiên!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (3, 'Trần Thị Mai', 5, 'Bánh mì ngon nhất tôi từng ăn, sẽ mua định kỳ.', NOW());

-- ==================== SẢN PHẨM 4 ====================
INSERT IGNORE INTO products (name, price, description, detail_description, storage_guide, collection, shipping_info, ingredients, fresh_guarantee)
VALUES (
           'Strawberry Macaron Box',
           6.50,
           'Hộp macaron dâu tây tinh tế với vỏ ngoài giòn nhẹ, nhân kem dâu tươi mát.',
           'Macaron được làm theo công thức Pháp truyền thống với bột hạnh nhân xay mịn. Nhân kem dâu tây tươi được làm mới mỗi ngày, không dùng hương liệu nhân tạo.',
           'Bảo quản ngăn mát, dùng trong 3 ngày. Lấy ra khỏi tủ lạnh 15 phút trước khi ăn để đạt hương vị tốt nhất.',
           'French Patisserie Collection',
           'Giao hàng nhanh trong 2h tại nội thành',
           'Bột hạnh nhân,Đường bột,Lòng trắng trứng,Dâu tây tươi,Kem tươi',
           true
       );
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (4, 'https://images.unsplash.com/photo-1569864358642-9d1684040f43?w=800', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (4, 'https://images.unsplash.com/photo-1558326567-98ae2405596b?w=800', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (4, 'https://images.unsplash.com/photo-1612809075429-d76f5f6eacb6?w=800', 2);
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (4, 'Võ Thị Bích', 5, 'Macaron vỏ giòn, nhân kem dâu tươi ngon xuất sắc!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (4, 'Đinh Hoàng Nam', 4, 'Đẹp mắt, ngon miệng, đóng gói cẩn thận.', NOW());

-- ==================== SẢN PHẨM 5 ====================
INSERT IGNORE INTO products (name, price, description, detail_description, storage_guide, collection, shipping_info, ingredients, fresh_guarantee)
VALUES (
           'Valentine Cupcakes',
           8.00,
           'Cupcake Valentine ngọt ngào với lớp buttercream hoa hồng và trang trí trái tim.',
           'Mỗi chiếc cupcake được nướng từ bột vanilla Madagascar và phủ lớp buttercream Thụy Sĩ đánh bông mịn. Trang trí thủ công bằng kỹ thuật piping hoa hồng tinh tế.',
           'Bảo quản nhiệt độ phòng trong 1 ngày hoặc ngăn mát 3 ngày. Không để trực tiếp dưới ánh nắng.',
           'Seasonal Collection',
           'Giao hàng nhanh trong 2h tại nội thành',
           'Bột mì,Bơ nhạt,Đường,Trứng gà,Vanilla Madagascar,Kem tươi',
           true
       );
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (5, 'https://images.unsplash.com/photo-1519869325930-281384150729?w=800', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (5, 'https://images.unsplash.com/photo-1563729784474-d77dbb933a9e?w=800', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (5, 'https://images.unsplash.com/photo-1587668178277-295251f900ce?w=800', 2);
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (5, 'Nguyễn Thị Thu', 5, 'Tặng người yêu cực kỳ ý nghĩa, bánh ngon và đẹp!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (5, 'Lý Minh Khoa', 5, 'Buttercream không quá ngọt, rất vừa miệng.', NOW());

-- ==================== SẢN PHẨM 6 ====================
INSERT IGNORE INTO products (name, price, description, detail_description, storage_guide, collection, shipping_info, ingredients, fresh_guarantee)
VALUES (
           'Vanilla Buttercream Cupcakes',
           24.00,
           'Hộp 6 cupcake vanilla với lớp buttercream bơ Pháp mịn màng, thơm ngát.',
           'Cupcake vanilla được làm từ vanilla bean thật nhập khẩu từ Madagascar. Lớp buttercream được đánh theo kỹ thuật Pháp với bơ AOP để tạo nên kết cấu mịn như lụa.',
           'Bảo quản ngăn mát, dùng trong 3 ngày. Để ở nhiệt độ phòng 20 phút trước khi thưởng thức.',
           'Classic Collection',
           'Giao hàng nhanh trong 2h tại nội thành',
           'Bơ AOP Pháp,Vanilla bean Madagascar,Bột mì,Đường mía,Trứng gà thả vườn',
           true
       );
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (6, 'https://images.unsplash.com/photo-1486427944299-d1955d23e34d?w=800', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (6, 'https://images.unsplash.com/photo-1576618148400-f54bed99fcfd?w=800', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (6, 'https://images.unsplash.com/photo-1614707267537-b85aaf00c4b7?w=800', 2);
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (6, 'Trần Thanh Tuyền', 5, 'Vanilla thơm tự nhiên, không ngửi mùi hóa chất.', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (6, 'Hoàng Đức Thịnh', 4, 'Ngon, đóng gói đẹp, phù hợp làm quà tặng.', NOW());

-- ==================== SẢN PHẨM 7 ====================
INSERT IGNORE INTO products (name, price, description, detail_description, storage_guide, collection, shipping_info, ingredients, fresh_guarantee)
VALUES (
           'Matcha Cream Roll Cake',
           38.00,
           'Bánh cuộn matcha Uji Nhật Bản với nhân kem tươi béo ngậy, thanh mát.',
           'Bánh cuộn sử dụng matcha ceremonial grade Uji từ Kyoto, Nhật Bản. Lớp bánh bông lan mềm mịn được cuộn cùng nhân kem tươi đánh bông nhẹ nhàng, tạo nên sự cân bằng giữa vị đắng của matcha và độ béo của kem.',
           'Bảo quản ngăn mát 2-5°C, dùng trong 2 ngày. Không để đông đá.',
           'Japanese Collection',
           'Giao hàng nhanh trong 2h tại nội thành',
           'Matcha Uji Nhật Bản,Kem tươi 35%,Trứng gà,Bột mì,Đường,Bơ nhạt',
           true
       );
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (7, 'https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=800', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (7, 'https://images.unsplash.com/photo-1548365328-8c6db3220e4d?w=800', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (7, 'https://images.unsplash.com/photo-1607478900766-efe13248b125?w=800', 2);
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (7, 'Phạm Thị Linh', 5, 'Matcha thơm đúng chuẩn Nhật, không bị đắng gắt!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (7, 'Bùi Quang Huy', 5, 'Kem tươi mát, bánh mềm, ăn một lần là ghiền.', NOW());

-- ==================== SẢN PHẨM 8 ====================
INSERT IGNORE INTO products (name, price, description, detail_description, storage_guide, collection, shipping_info, ingredients, fresh_guarantee)
VALUES (
           'Croissant Beurre AOP',
           4.50,
           'Bánh sừng bò bơ Pháp cán tay với 27 lớp bơ mỏng, giòn rụm bên ngoài, mềm xốp bên trong.',
           'Croissant được cán tay qua 6 lần gấp tạo nên 27 lớp bơ mỏng hoàn hảo. Sử dụng bơ AOP Charentes-Poitou nhập khẩu từ Pháp, ủ lạnh qua đêm để đạt hương vị tốt nhất.',
           'Ngon nhất khi ăn trong ngày. Có thể hâm lò 170°C trong 3 phút để lấy lại độ giòn.',
           'French Patisserie Collection',
           'Giao hàng nhanh trong 2h tại nội thành',
           'Bơ AOP Charentes-Poitou,Bột mì T45,Men nở,Muối,Sữa tươi,Đường',
           true
       );
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (8, 'https://images.unsplash.com/photo-1555507036-ab1f4038808a?w=800', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (8, 'https://images.unsplash.com/photo-1623334044303-241021148842?w=800', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (8, 'https://images.unsplash.com/photo-1608198093002-ad4e005484ec?w=800', 2);
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (8, 'Lê Văn Đức', 5, 'Croissant giòn tan, bơ thơm, đúng chuẩn Pháp!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (8, 'Nguyễn Bảo Châu', 5, 'Ăn kèm cà phê sáng thì không gì bằng.', NOW());

-- ==================== SẢN PHẨM 9 ====================
INSERT IGNORE INTO products (name, price, description, detail_description, storage_guide, collection, shipping_info, ingredients, fresh_guarantee)
VALUES (
           'Lemon Tart Provence',
           32.00,
           'Tart chanh vàng kiểu Provence với vỏ tart giòn, nhân lemon curd chua nhẹ và meringue nướng.',
           'Vỏ tart pâte sucrée được làm từ bột mì T55 và bơ AOP, nướng mù cho đến khi vàng đều. Nhân lemon curd được nấu từ chanh vàng Provence tươi, cân bằng giữa chua và ngọt. Phủ meringue Ý đánh bông và nướng đèn khò tạo hiệu ứng caramel đẹp mắt.',
           'Bảo quản ngăn mát, dùng trong 2 ngày. Không để đông đá vì meringue sẽ chảy nước.',
           'French Patisserie Collection',
           'Giao hàng nhanh trong 2h tại nội thành',
           'Chanh vàng Provence,Bơ AOP,Trứng gà,Đường,Bột mì T55,Lòng trắng trứng',
           true
       );
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (9, 'https://images.unsplash.com/photo-1519915028121-7d3463d20b13?w=800', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (9, 'https://images.unsplash.com/photo-1568051243858-533a607809a5?w=800', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (9, 'https://images.unsplash.com/photo-1587314168485-3236d6710814?w=800', 2);
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (9, 'Trương Thị Hạnh', 5, 'Lemon curd chua nhẹ, meringue ngọt vừa, tuyệt vời!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (9, 'Vũ Minh Tuấn', 4, 'Vỏ tart giòn tan, nhân mịn màng. Rất ấn tượng!', NOW());

-- ==================== SẢN PHẨM 10 ====================
INSERT IGNORE INTO products (name, price, description, detail_description, storage_guide, collection, shipping_info, ingredients, fresh_guarantee)
VALUES (
           'Tiramisu Classico',
           42.00,
           'Tiramisu truyền thống Ý với lớp mascarpone béo ngậy, thấm đượm cà phê espresso đậm đà.',
           'Tiramisu được làm theo công thức gốc từ vùng Veneto, Ý. Sử dụng phô mai mascarpone Ý nhập khẩu, trứng gà tươi đánh kiểu zabaglione truyền thống và espresso nguyên chất. Bánh được ủ lạnh 12 giờ để các lớp hòa quyện hoàn hảo.',
           'Bảo quản ngăn mát 2-5°C, dùng trong 3 ngày. Phục vụ lạnh để có hương vị tốt nhất.',
           'Italian Collection',
           'Giao hàng nhanh trong 2h tại nội thành',
           'Mascarpone Ý,Trứng gà tươi,Espresso nguyên chất,Bánh ladyfinger,Đường,Cacao nguyên chất',
           true
       );
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (10, 'https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=800', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (10, 'https://images.unsplash.com/photo-1551529834-525807d6b4f3?w=800', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES (10, 'https://images.unsplash.com/photo-1568051243851-f9b136146e97?w=800', 2);
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (10, 'Đặng Thị Phương', 5, 'Tiramisu đúng vị Ý, cà phê thơm, mascarpone béo ngậy!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES (10, 'Cao Văn Long', 5, 'Ngon không thua gì nhà hàng Ý chính hiệu!', NOW());