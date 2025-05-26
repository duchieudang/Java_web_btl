<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8">
<title>Đặt phòng</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">

<%@ include file="fragments2/include-css.jsp"%>

</head>

<body>
	<div class="container-xxl bg-white p-0">
		<%@ include file="fragments2/spinner.jsp"%>
		<%@ include file="fragments2/header.jsp"%>


		<!-- Room Detail Start -->
		<div class="container-xxl py-5">
			<div class="container">
				<div class="row g-5">
					<div class="col-12 px-4">

						<c:if test="${flag == 'ok'}">
							<div class="alert alert-success my-0">
								<strong>Đã gửi đơn thành công, vui lòng để ý mail hoặc
									điện thoại từ nhân viên để nhận thông tin phòng của bạn.</strong> <br />Hãy
								lưu lại mã đặt phòng sau:
								<h3>${uuid}</h3>
								
							</div>

						</c:if>
						<c:if test="${flag == 'fail'}">
							<div class="alert alert-warning my-0">${message}</div>
						</c:if>
						<c:if test="${flag == 'param'}">
							<div class="alert alert-warning my-0">${message}</div>
						</c:if>
					</div>
					<!-- Detail Start -->
					<div class="col-lg-8">
						<!--                         <div id="room-carousel" class="carousel slide mb-5 wow fadeIn" data-bs-ride="carousel" data-wow-delay="0.1s"> -->
						<!--                             <div class="carousel-inner"> -->
						<!--                                 <div class="carousel-item active"> -->
						<!--                                     <img class="w-100" src="img/carousel-1.jpg" alt="Image"> -->
						<!--                                 </div> -->
						<!--                                 <div class="carousel-item"> -->
						<%--                                     <img style="width: 100%; height: auto; aspect-ratio: 16 / 9; object-fit: cover;" src="data:image/jpeg;base64,${room.roomImageBase64}" alt="${room.roomName}"> --%>
						<!--                                 </div> -->
						<!--                             </div> -->
						<!--                             <button class="carousel-control-prev" type="button" data-bs-target="#room-carousel" -->
						<!--                                 data-bs-slide="prev"> -->
						<!--                                 <span class="carousel-control-prev-icon" aria-hidden="true"></span> -->
						<!--                                 <span class="visually-hidden">Previous</span> -->
						<!--                             </button> -->
						<!--                             <button class="carousel-control-next" type="button" data-bs-target="#room-carousel" -->
						<!--                                 data-bs-slide="next"> -->
						<!--                                 <span class="carousel-control-next-icon" aria-hidden="true"></span> -->
						<!--                                 <span class="visually-hidden">Next</span> -->
						<!--                             </button> -->
						<!--                         </div> -->
						<img class="mb-5"
							style="width: 100%; height: auto; aspect-ratio: 16/9; object-fit: cover;"
							src="data:image/jpeg;base64,${room.roomImageBase64}"
							alt="${room.roomName}">

						<div class="d-flex align-items-center mb-4">
							<%--                             <h1 class="mb-0"><%= roomName %></h1> --%>
							<h1 class="mb-0">${room.roomName}</h1>
							<div class="ps-3">
								<c:forEach var="abc" begin="1" end="${room.roomStarCount}">
									<small class="fa fa-star text-primary"></small>
								</c:forEach>
							</div>
						</div>

						<div class="d-flex flex-wrap pb-4 m-n1">
							<small class="bg-light rounded py-1 px-3 m-1"><i
								class="fa fa-bed text-primary me-2"></i>${room.roomBedCount}
								Giường</small> <small class="bg-light rounded py-1 px-3 m-1"><i
								class="fa fa-bath text-primary me-2"></i>${room.roomSize} m<sup>2</sup></small>
							<!--                             <small class="bg-light rounded py-1 px-3 m-1"><i class="fa fa-wifi text-primary me-2"></i>Wifi</small> -->
							<!--                             <small class="bg-light rounded py-1 px-3 m-1"><i class="fa fa-tv text-primary me-2"></i>TV</small> -->
							<!--                             <small class="bg-light rounded py-1 px-3 m-1"><i class="fa fa-fan text-primary me-2"></i>AC</small> -->
							<!--                             <small class="bg-light rounded py-1 px-3 m-1"><i class="fa fa-user-cog text-primary me-2"></i>Laundry</small> -->
							<!--                             <small class="bg-light rounded py-1 px-3 m-1"><i class="fa fa-utensils text-primary me-2"></i>Dinner</small> -->
						</div>

						<p>${room.roomNote }</p>
					</div>
					<!-- Detail End -->

					<!-- Sidebar Start -->
					<div class="col-lg-4">
						<!-- Booking Start -->
						<form class="bg-light mb-5 wow fadeInUp" data-wow-delay="0.1s"
							method="post">
							<div class="border-bottom text-center text-dark p-3 pt-4 mb-3">
								<span class="align-middle fs-1 lh-sm fw-bold">${room.roomPricePerHourVnd }₫</span>
								<!--                                 <span class="align-top fs-4 lh-base"></span> -->
								<span class="align-bottom fs-6 lh-lg">/ Đêm</span>
							</div>

							<input type="hidden" name="room_id" value="${room.roomId }"></input>

							<div class="row g-3 p-4 pt-2">
								<div class="col-12">
									<label class="mb-1">Ngày bắt đầu</label> <input type="date"
										name="booking_start_date"
										class="form-control datetimepicker-input" />
								</div>

								<div class="col-12">
									<label class="mb-1">Ngày kết thúc</label> <input type="date"
										name="booking_end_date"
										class="form-control datetimepicker-input" />
								</div>

								<div class="col-12">
									<label class="mb-1">Số CCCD/Hộ chiếu:</label> <input
										type="text" name="customer_contact1" class="form-control" />
								</div>
								<div class="col-12">
									<label class="mb-1">Email hoặc SĐT</label> <input type="text"
										name="customer_contact" class="form-control" />
								</div>

								<div class="col-12">
									<label class="mb-1">Số người</label> <select
										name="booking_people_count" class="form-select">
										<option value="1">1 người</option>
										<option value="2">2 người</option>
										<option value="3">3 người</option>
										<option value="3">4 người</option>
										<option value="3">5 người</option>
									</select>
								</div>

								<div class="col-12">
									<label class="mb-1">Ghi chú</label>
									<textarea name="booking_note" rows="3" class="form-control"></textarea>
								</div>



								<div class="col-12">
									<button type="submit" class="btn btn-primary py-3 w-100">Đặt
										phòng</button>
								</div>
							</div>
						</form>
						<!-- Booking End -->


					</div>
					<!-- Sidebar End -->
				</div>
			</div>
		</div>
		<!-- Room Detail End -->



		<%@ include file="fragments2/footer.jsp"%>


		<!-- Back to Top -->
		<a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i
			class="bi bi-arrow-up"></i></a>
	</div>

	<%@ include file="fragments2/include-js.jsp"%>
</html>