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

INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Red Velvet Muse Cake' LIMIT 1), 'https://i.pinimg.com/736x/df/91/15/df9115d746533697591d7c37d93ccf8c.jpg', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Red Velvet Muse Cake' LIMIT 1), 'https://i.pinimg.com/736x/79/0c/8b/790c8b07d5869dee8ceab42ea5ed8d8f.jpg', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Red Velvet Muse Cake' LIMIT 1), 'https://i.pinimg.com/736x/50/94/5c/50945cc192b4c0d8cb37669447fe2531.jpg', 2);

INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Red Velvet Muse Cake' LIMIT 1), 'Nguyễn Thị Lan', 5, 'Bánh ngon tuyệt vời, kem cheese rất mịn!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Red Velvet Muse Cake' LIMIT 1), 'Trần Văn Minh', 5, 'Giao hàng nhanh, bánh còn tươi, sẽ ủng hộ tiếp!', NOW());


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

INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Chocolate Fudge Truffle Cheesecake' LIMIT 1), 'https://i.pinimg.com/736x/16/46/3d/16463d7aa39c47d3f720ef0ec932c885.jpg', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Chocolate Fudge Truffle Cheesecake' LIMIT 1), 'https://i.pinimg.com/736x/9e/8f/60/9e8f6043aa8932de53eac9063c945a36.jpg', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Chocolate Fudge Truffle Cheesecake' LIMIT 1), 'https://i.pinimg.com/1200x/0a/b8/35/0ab835e98e4a2d2081bed5bf3882f1d5.jpg', 2);

INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Chocolate Fudge Truffle Cheesecake' LIMIT 1), 'Lê Thị Hoa', 5, 'Socola đậm đà, cheesecake không bị ngấy. Cực phẩm!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Chocolate Fudge Truffle Cheesecake' LIMIT 1), 'Phạm Quốc Bảo', 4, 'Ngon lắm, lần sau sẽ order thêm!', NOW());


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

INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Signature Sourdough Bread' LIMIT 1), 'https://i.pinimg.com/736x/30/9d/63/309d63c5fb68a856a69a0803ecd90068.jpg', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Signature Sourdough Bread' LIMIT 1), 'https://i.pinimg.com/1200x/05/35/57/05355798c8ae1ec510b1c26f93a16f1e.jpg', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Signature Sourdough Bread' LIMIT 1), 'https://i.pinimg.com/736x/9a/a1/e6/9aa1e6b241e858608af1c5b947b6430c.jpg', 2);

INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Signature Sourdough Bread' LIMIT 1), 'Nguyễn Văn An', 5, 'Vỏ giòn, ruột dai, hương vị lên men rất tự nhiên!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Signature Sourdough Bread' LIMIT 1), 'Trần Thị Mai', 5, 'Bánh mì ngon nhất tôi từng ăn, sẽ mua định kỳ.', NOW());


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

INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Strawberry Macaron Box' LIMIT 1), 'https://i.pinimg.com/736x/38/70/22/38702204789a6531c5ad8f8ba9c1c982.jpg', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Strawberry Macaron Box' LIMIT 1), 'https://i.pinimg.com/736x/d2/08/c7/d208c7e2f5509c9bc8e2044c582ca304.jpg', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Strawberry Macaron Box' LIMIT 1), 'https://i.pinimg.com/1200x/67/f3/57/67f35778aed17258ba89bd943e930c78.jpg', 2);

INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Strawberry Macaron Box' LIMIT 1), 'Võ Thị Bích', 5, 'Macaron vỏ giòn, nhân kem dâu tươi ngon xuất sắc!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Strawberry Macaron Box' LIMIT 1), 'Đinh Hoàng Nam', 4, 'Đẹp mắt, ngon miệng, đóng gói cẩn thận.', NOW());


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

INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Valentine Cupcakes' LIMIT 1), 'https://i.pinimg.com/1200x/9b/98/c7/9b98c7ec8a35300f627cad7047c9563e.jpg', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Valentine Cupcakes' LIMIT 1), 'https://i.pinimg.com/1200x/93/f1/50/93f150fa54545c8e3b3bde8179bae25b.jpg', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Valentine Cupcakes' LIMIT 1), 'https://i.pinimg.com/736x/b6/e2/3e/b6e23e64fcefb5650bc8753f360b6359.jpg', 2);

INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Valentine Cupcakes' LIMIT 1), 'Nguyễn Thị Thu', 5, 'Tặng người yêu cực kỳ ý nghĩa, bánh ngon và đẹp!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Valentine Cupcakes' LIMIT 1), 'Lý Minh Khoa', 5, 'Buttercream không quá ngọt, rất vừa miệng.', NOW());


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

INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Vanilla Buttercream Cupcakes' LIMIT 1), 'https://i.pinimg.com/1200x/95/0a/0a/950a0a62dcebd0b0d9721751c7367d0e.jpg', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Vanilla Buttercream Cupcakes' LIMIT 1), 'https://i.pinimg.com/736x/d2/68/76/d268767e9fe329d7bea9bfb3da683a54.jpg', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Vanilla Buttercream Cupcakes' LIMIT 1), 'https://i.pinimg.com/736x/a7/99/54/a79954a884dda23f91291ac0cfb18986.jpg', 2);

INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Vanilla Buttercream Cupcakes' LIMIT 1), 'Trần Thanh Tuyền', 5, 'Vanilla thơm tự nhiên, không ngửi mùi hóa chất.', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Vanilla Buttercream Cupcakes' LIMIT 1), 'Hoàng Đức Thịnh', 4, 'Ngon, đóng gói đẹp, phù hợp làm quà tặng.', NOW());


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

INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Matcha Cream Roll Cake' LIMIT 1), 'https://i.pinimg.com/1200x/ab/4a/e5/ab4ae58febbddfcc24a2539740847ae0.jpg', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Matcha Cream Roll Cake' LIMIT 1), 'https://i.pinimg.com/736x/5e/9c/d4/5e9cd4f6df6b73bbbfd79cb7636f811e.jpg', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Matcha Cream Roll Cake' LIMIT 1), 'https://i.pinimg.com/736x/cc/1b/f7/cc1bf7fa1c5dc8450ccb37b39a4b6a51.jpg', 2);

INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Matcha Cream Roll Cake' LIMIT 1), 'Phạm Thị Linh', 5, 'Matcha thơm đúng chuẩn Nhật, không bị đắng gắt!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Matcha Cream Roll Cake' LIMIT 1), 'Bùi Quang Huy', 5, 'Kem tươi mát, bánh mềm, ăn một lần là ghiền.', NOW());


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

INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Croissant Beurre AOP' LIMIT 1), 'https://i.pinimg.com/1200x/ff/f4/ac/fff4ac614be3b1154adefe7a97fa2476.jpg', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Croissant Beurre AOP' LIMIT 1), 'https://i.pinimg.com/736x/d1/03/21/d103213d7a41621227147a43a7ff06b8.jpg', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Croissant Beurre AOP' LIMIT 1), 'https://i.pinimg.com/736x/ef/28/f0/ef28f057274dc03db130c5453e4ea253.jpg', 2);

INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Croissant Beurre AOP' LIMIT 1), 'Lê Văn Đức', 5, 'Croissant giòn tan, bơ thơm, đúng chuẩn Pháp!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Croissant Beurre AOP' LIMIT 1), 'Nguyễn Bảo Châu', 5, 'Ăn kèm cà phê sáng thì không gì bằng.', NOW());


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

INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Lemon Tart Provence' LIMIT 1), 'https://i.pinimg.com/1200x/ed/e8/a3/ede8a30b7796ce7932610408ddeebec4.jpg', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Lemon Tart Provence' LIMIT 1), 'https://i.pinimg.com/736x/68/87/f1/6887f11e8c77012979adffba2dba5a03.jpg', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Lemon Tart Provence' LIMIT 1), 'https://i.pinimg.com/736x/93/1e/7c/931e7c26de3b6376f34715d7c2d3ae9c.jpg', 2);

INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Lemon Tart Provence' LIMIT 1), 'Trương Thị Hạnh', 5, 'Lemon curd chua nhẹ, meringue ngọt vừa, tuyệt vời!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Lemon Tart Provence' LIMIT 1), 'Vũ Minh Tuấn', 4, 'Vỏ tart giòn tan, nhân mịn màng. Rất ấn tượng!', NOW());


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

INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Tiramisu Classico' LIMIT 1), 'https://i.pinimg.com/736x/15/10/17/1510174879cf77169e64a5c82ae973c1.jpg', 0);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Tiramisu Classico' LIMIT 1), 'https://i.pinimg.com/736x/8f/dd/a5/8fdda5e47db5a90879a2cee8002fb345.jpg', 1);
INSERT IGNORE INTO product_images (product_id, image_url, sort_order) VALUES ((SELECT id FROM products WHERE name = 'Tiramisu Classico' LIMIT 1), 'https://i.pinimg.com/1200x/11/36/4d/11364d08134253c7d88052095ef5a67e.jpg', 2);

INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Tiramisu Classico' LIMIT 1), 'Đặng Thị Phương', 5, 'Tiramisu đúng vị Ý, cà phê thơm, mascarpone béo ngậy!', NOW());
INSERT IGNORE INTO reviews (product_id, customer_name, rating, comment, created_at) VALUES ((SELECT id FROM products WHERE name = 'Tiramisu Classico' LIMIT 1), 'Cao Văn Long', 5, 'Ngon không thua gì nhà hàng Ý chính hiệu!', NOW());


UPDATE products SET current_price = price WHERE current_price IS NULL;