package roomescape.reservation.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import roomescape.member.domain.Member;
import roomescape.theme.domain.Theme;
import roomescape.time.domain.ReservationTime;

import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    private LocalDate date;
    @JoinColumn(name = "time_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ReservationTime time;
    @JoinColumn(name = "theme_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Theme theme;
    @Enumerated(value = EnumType.STRING)
    @ColumnDefault(value = "'CONFIRMATION'")
    private ReservationStatus status;

    public Reservation() {
    }

    public Reservation(Long id, Member member, LocalDate date, ReservationTime time, Theme theme, ReservationStatus status) {
        validate(member, date, time, theme);
        this.id = id;
        this.member = member;
        this.date = date;
        this.time = time;
        this.theme = theme;
        this.status = status;
    }

    private void validate(Member member, LocalDate date, ReservationTime time, Theme theme) {
        validateNullField(member, date, time, theme);
        validateName(member.getName());
    }

    private void validateNullField(Member member, LocalDate date, ReservationTime time, Theme theme) {
        if (member == null || date == null || time == null || theme == null) {
            throw new IllegalArgumentException("예약 필드에는 빈 값이 들어올 수 없습니다.");
        }
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("이름은 공백일 수 없습니다.");
        }
    }

    public Reservation(Long id, Member member, LocalDate date, ReservationTime time, Theme theme) {
        this(id, member, date, time, theme, ReservationStatus.CONFIRMATION);
    }

    public Reservation(Member member, LocalDate date, ReservationTime reservationTime, Theme theme) {
        this(null, member, date, reservationTime, theme);
    }

    public boolean isSameMember(Reservation other) {
        return member.isSameMember(other.member);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getTime() {
        return time;
    }

    public Theme getTheme() {
        return theme;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}
