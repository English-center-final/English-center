# API Web Trung Tâm Tiếng Anh - Spring Boot

## API

### Auth `/auth`
- `[POST] /login`: đăng nhập.
	- body: {username: String, password: String}.
	- result: {accessToken: String}.
- `[POST] /registry`: đăng ký.
	- body: {name: String, username: String, password: String, phoneNumber: String}.
- `[POST] /confirm-account`: Xác nhận đăng ký.
	- body: {username: String, otp: String}.
	- result: {accessToken: String}.
- `[POST] /reset-otp`: Reset otp.
	- body: {username: String}.
- `[POST] /confirm-password`: Xác nhận mật khẩu mới.
	- body: {username: String, password: String, otp: String}.
	- result: {accessToken: String}.
	
### Me `/me`
- `[GET] /`:  get thông tin.
	- result: { id: String, name : String, username: String, phoneNumber: String, image: String, gender: boolean, actived: boolean, roles: []}.
- `[PUT] /`:  cập nhật thông tin.
	- body: {id: int, name : String,phoneNumber: String, gender: boolean}.
	- result: { id: String, name : String, username: String, phoneNumber: String, image: String, gender: boolean, actived: boolean, roles: []}.
- `[PUT] /image`: Cập nhật ảnh đại diện.
	- body: {image: File}.
  	- result: String.
- `[GET] /classes`:  get thông tin khóa học đã đăng ký.
	- result: [{ classesId: int, status : String }]
- `[PUT] /change-password`:  Đổi mật khẩu.
	- body: {username: String, password: String, otp: String}.
	- result: {accessToken: String}.
- `[GET] /exam-history`:  get danh sách kết bải thi thử đã làm.
	- params:
		- page: int (default: 0)
		- size: int (default: 12)

### Course
- `[GET] /courses/topics`: lấy danh sách topic.
- `[GET] /courses`: lấy danh sách khóa học từ vựng.
	- params: 
		- name: String
		- topicSlug: String
		- page: int (default: 0)
		- size: int (default: 12) .
- `[GET] /courses/:slug`: lấy chi tiết khóa học từ vựng

### Course Word
- `[GET] /course-words`: lấy từ vựng theo slug khóa học
	- params:
		- courseSlug: String
		- page: int (default: 0)
		- size: int (default: 30)

### Word Note `/user/word-note-categories`
- `[GET]`: lấy danh sách các danh mục ghi chú.
- `[POST]`: tạo danh mục ghi chú.
	- body: {name: String}.
- `[PUT] /:id`: Đổi tên danh mục ghi chú.
	- body: {name: String}.
- `[DELETE] /:id`: Xóa danh mục ghi chú.
- `[POST] /add-word`: Thêm từ vào danh mục ghi chú.
	- body: {wordNoteCategoryId: int, wordId: int}
- `[GET] /:id`: lấy chi tiết danh mục ghi chú.
- `[GET] /review/:id`: ôn tập từ vựng.
	- params:
  		- type: int (mặc định là 0)
  			- 0: 1 câu hỏi và 4 từ trắc nghiệm.
  			- 1: gợi ý và điền từ.
	- ids: List Integer: Danh sách ids đã ôn tập rồi.
- `[DELETE] /:id/words/:wordId`: xóa từ ra khỏi ghi chú.

### Book
- `[GET] /books`: lấy tên sách và đề thi của sách.

### Exam `/exams`
- `[GET] /:slug`: lấy câu hỏi của bài thi.
- `[POST] /:slug/result`: kiểm tra kết quả bài thi.
	- body:  Map<Integer, String> answers.
- `[GET]`: lấy tất cả tên và slug của bài thi.
- `[GET] /:slug/parts`: lấy câu hỏi của part theo đề thi.
	- params: type: int (từ 1 - 7).

### Level `/levels`
- `[GET]`: lấy danh sách khóa học.
	- params: 
		- name: String
- `[GET] /:slug`: lấy chi tiết khóa học

### Branch `/branches`
- `[GET]`: lấy danh sách chi nhánh.
	- params: 
		- name: String

### Classes `/classes`
- `[GET]`: lấy danh sách lớp học.
	- params: 
		- levelSlug: String
		- branchId: String
		- dateFrom: String (dd/mm/yyyy)
		- dateTo: String (dd/mm/yyyy)
		- page: int (default: 0)
		- size: int (default: 12).

- `[POST] /registry`: đăng ký khóa học.
	- body:  {classesId: int, status: 'NEW'}
		
- `[GET] /schedules`: Xem thời khóa biểu.
	- params: 
		- dateFrom: String (dd/mm/yyy)
		- dateTo: String (dd/mm/yyy)
		- page: int (default: 0)
		- size: int (default: 12).

## Api Admin

### Topic `/admin/courses/topics`
- `[POST]`: thêm topic.
	- body: {name: String}.
- `[PUT] /:id`: cập nhật topic.
	- body: {name: String}.
- `[DELETE] /:id`: xóa topic.

### Word `/admin/courses/words`
- `[GET]`: lấy danh sách word.
	- params:
  		- name: String (mặc định là "").
  		- page: int (mặc định là 0).
  		- size: int (mặc định là 10).
- `[POST]`: thêm word.
	- body: {name: String, mean: String, type: String, pronounce: String, definition: String, example: String}.
- `[PUT] /:id`: cập nhật word.
	- body: {name: String, mean: String, type: String, pronounce: String, definition: String, example: String}.
- `[DELETE] /:id`: xóa word.
- `[PUT] /:id/image`: cập nhật ảnh.
	- body: image: File.
- `[PUT] /:id/sound`: cập nhật âm thanh.
	- body: sound: File.

### Course `/admin/course`
- `[POST]`: thêm course.
	- body: {name: String, description: String, topicId: Integer}.
- `[PUT] /:id`: cập nhật course.
	- body: {name: String, description: String, topicId: Integer}.
- `[DELETE] /:id`: xóa course.
- `[PUT] /:id/image`: cập nhật ảnh.
	- body: image: File.
- `[POST] /:id/course-word`: thêm word vào course.
	- body: {wordId: Integer}.
- `[DELETE] /:id/course-word/:wordId`: xóa word ra khỏi course.

### User `/admin/users`.
- `[GET]`: get list.
	- params:
  		- username: string ("").
  		- page: int (0).
  		- size: int (10).
- `[POST]`: thêm.
	- body: {name: String, username: String, password: String}.
- `[DELETE] /:id`: xóa
- `[PUT] /:id/admin-role`: update quyền Admin.
- `[PUT] /:id/update-roles`: update danh sách quyền.
	- body:  List<String> roles.
- `[POST] /new-user`: thêm người dùng mới.
	- body: {name: String, username: String, password: String, phoneNumber: String}.
- `[PUT] /new-user`: Cập nhật trạng thái người dùng.
	- body: {id: int, actived: boolean}.

### Book `/admin/exams/books`
- `[POST]`: thêm sách.
	- body: {name: String}.
- `[PUT] /:id`: cập nhật sách.
	- body: {name: String}.
- `[DELETE] /:id`: xóa sách.
- `[PUT] /:id/image`: cập nhật ảnh cho sách.

### Exam `/admin/exams`
- `[GET]`: lấy danh sách exam.
	- params:
  		- name: String (mặc định là "").
  		- bookName: String (mặc định là "").
  		- page: int (mặc định là 0).
  		- size: int (mặc định là 10).
- `[POST]`: thêm exam.
	- params: stts[int]
	- body: {name: String, bookId: Integer, bookName: String}.
- `[PUT] /{id}`: update exam.
	- body: {name: Integer, bookId: Integer, bookName: String}.
- `[DELETE] /{id}`: xóa exam.
- `[PUT] /{id}/audio`: upload audio exam.
	- body: {part1Audio: File, part2Audio: File, part3Audio: File, part4Audio: File }
  
### Paragraph `/admin/exams/paragraphs`.
- `[PUT] /{id}`: update .
	- body: {content: String, transcript: String}.
- `[PUT] /{id}/image`: update image .
	- body: {image: File}.
- `[PUT] /{id}/audio`: update audio cho part 3, 4 .
	- body: {audio: File}.
  
### Question `/admin/exams/questions`.
- `[GET]`: get list.
	- params:
  		- examId: int.
  		- type: int.
- `[PUT] /{id}`: update.
	- body: {content: String, a: String, b: String, c: String, d: String, result: String, extra: String}.
- `[PUT] /{id}/image`: update image cho part 1 .
	- body: {image: File}.
- `[PUT] /{id}/audio`: update audio cho part 1, 2.
	- body: {audio: File}.
	
### Level `/admin/levels`.
- `[POST]`: thêm level.
	- body: {name: String, description: String, content: String}.
- `[PUT] /:id`: cập nhật level.
	- body: {name: String, description: String, content: String}.
- `[DELETE] /:id`: xóa level.
- `[PUT] /:id/image`: cập nhật ảnh.
	- body: image: File.
	
### Branch `/admin/branches`.
- `[POST]`: thêm chi nhánh.
	- body: {name: String, address: String}.
- `[PUT] /:id`: cập nhật chi nhánh.
	- body: {name: String, address: String}.
- `[DELETE] /:id`: xóa chi nhánh.

### Classes `/admin/classes`.
- `[POST]`: thêm classes.
	- body: 
		- amount: int,
    	- dateStart: String,
    	- numOfLessons: int,
    	- status: String (ONGOING, FULL, CANCEL, ACCEPT),
    	- description: String,
    	- date: String (ex: "TUESDAY, THURSDAY, SATURDAY"),
    	- levelId: int,
    	- branchId: int,
    	- room: String,
    	- session: String .
    	
- `[PUT] /:id`: cập nhật classes.
	- body: 
		- amount: int,
    	- dateStart: String,
    	- numOfLessons: int,
    	- status: String (ONGOING, FULL, CANCEL, ACCEPT),
    	- description: String,
    	- date: String (ex: "TUESDAY, THURSDAY, SATURDAY"),
    	- levelId: int,
    	- branchId: int,
    	- room: String,
    	- session: String .
- `[DELETE] /:id`: Xóa classes.

- `[GET]/:id`: lấy chi tiết lớp theo id.

- `[GET]/:id/schedules`: lấy danh sách thời khóa biểu theo lớp.
	- params: 
		- dateFrom: String (dd/mm/yyy)
		- dateTo: String (dd/mm/yyy)
		- page: int (default: 0)
		- size: int (default: 12).

- `[GET]/register`: lấy danh sách học viên đăng ký.
	- params: 
		- name: String
		- classId: int
		- levelSlug: String
		- status: String (NEW, CALLED, CANCEL, ACCEPT)
		- page: int (default: 0)
		- size: int (default: 12).
		
- `[POST]/register`: Thêm học viên vào lớp.
	- body: 
		- classesId: int,
    	- userId: int,
    	- status: String (ONGOING, FULL, CANCEL, ACCEPT),
    	
- `[PUT]/register`: Cập nhật trạng thái học viên.
	- body: 
		- classesId: int,
    	- userId: int,
    	- status: String (ONGOING, FULL, CANCEL, ACCEPT),
		

### Schedule `/admin/schedules`.
- `[POST]`: thêm thời schedule.
	- body: 
		- date: String (dd/mm/yyyy),
    	- description: String,
    	- room: String,
    	- session: String.
    	- status: boolean,
    	- classId: String,
- `[PUT] /:id`: cập nhật schedule.
	- body: 
		- date: String (dd/mm/yyyy),
    	- description: String,
    	- room: String,
    	- session: String.
    	- status: boolean,
    	- classId: String,
- `[DELETE] /:id`: Xóa schedule.
		