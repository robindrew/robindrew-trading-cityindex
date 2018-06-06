package com.robindrew.trading.cityindex;

import static com.robindrew.trading.Instruments.AUD_USD;
import static com.robindrew.trading.Instruments.BITCOIN;
import static com.robindrew.trading.Instruments.DAX_30;
import static com.robindrew.trading.Instruments.DOW_JONES_30;
import static com.robindrew.trading.Instruments.ETHER;
import static com.robindrew.trading.Instruments.EUR_JPY;
import static com.robindrew.trading.Instruments.EUR_USD;
import static com.robindrew.trading.Instruments.FTSE_100;
import static com.robindrew.trading.Instruments.GBP_USD;
import static com.robindrew.trading.Instruments.LITECOIN;
import static com.robindrew.trading.Instruments.RIPPLE;
import static com.robindrew.trading.Instruments.SP_500;
import static com.robindrew.trading.Instruments.USD_CHF;
import static com.robindrew.trading.Instruments.USD_JPY;
import static com.robindrew.trading.Instruments.XAG_USD;
import static com.robindrew.trading.Instruments.XAU_USD;
import static com.robindrew.trading.cityindex.InstrumentCategory.DFT;

import java.util.ArrayList;
import java.util.List;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instrument;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.price.precision.PricePrecision;
import com.robindrew.trading.provider.ITradingProvider;
import com.robindrew.trading.provider.TradingProvider;

public class CityIndexInstrument extends Instrument implements ICityIndexInstrument {

	/** AUD/USD. */
	public static final CityIndexInstrument SPOT_AUD_USD = new CityIndexInstrument(400616153, DFT, AUD_USD, 6);
	/** EUR/JPY. */
	public static final CityIndexInstrument SPOT_EUR_JPY = new CityIndexInstrument(400616147, DFT, EUR_JPY, 6);
	/** EUR/USD. */
	public static final CityIndexInstrument SPOT_EUR_USD = new CityIndexInstrument(400616149, DFT, EUR_USD, 6);
	/** GBP/USD. */
	public static final CityIndexInstrument SPOT_GBP_USD = new CityIndexInstrument(400616150, DFT, GBP_USD, 6);
	/** USD/CHF. */
	public static final CityIndexInstrument SPOT_USD_CHF = new CityIndexInstrument(400616148, DFT, USD_CHF, 6);
	/** USD/JPY. */
	public static final CityIndexInstrument SPOT_USD_JPY = new CityIndexInstrument(400616151, DFT, USD_JPY, 6);

	/** Bitcoin. */
	public static final CityIndexInstrument SPOT_BITCOIN = new CityIndexInstrument(401379436, DFT, BITCOIN, 2);
	/** Ether. */
	public static final CityIndexInstrument SPOT_ETHER = new CityIndexInstrument(401497467, DFT, ETHER, 2);
	/** Ripple. */
	public static final CityIndexInstrument SPOT_RIPPLE = new CityIndexInstrument(401497526, DFT, RIPPLE, 2);
	/** Litecoin. */
	public static final CityIndexInstrument SPOT_LITECOIN = new CityIndexInstrument(401497517, DFT, LITECOIN, 2);

	/** FTSE 100. */
	public static final CityIndexInstrument SPOT_FTSE_100 = new CityIndexInstrument(400616113, DFT, FTSE_100, 2);
	/** DAX. */
	public static final CityIndexInstrument SPOT_DAX = new CityIndexInstrument(400616115, DFT, DAX_30, 2);
	/** S&amp;P 500. */
	public static final CityIndexInstrument SPOT_SP_500 = new CityIndexInstrument(400616141, DFT, SP_500, 2);
	/** DOW JONES. */
	public static final CityIndexInstrument SPOT_DOW_JONES = new CityIndexInstrument(400616114, DFT, DOW_JONES_30, 2);

	/** GOLD. */
	public static final CityIndexInstrument SPOT_GOLD = new CityIndexInstrument(400616377, DFT, XAU_USD, 2);
	/** SILVER. */
	public static final CityIndexInstrument SPOT_SILVER = new CityIndexInstrument(400616380, DFT, XAG_USD, 2);

	public static List<CityIndexInstrument> values() {
		List<CityIndexInstrument> list = new ArrayList<>();

		list.add(SPOT_AUD_USD);
		list.add(SPOT_EUR_JPY);
		list.add(SPOT_EUR_USD);
		list.add(SPOT_GBP_USD);
		list.add(SPOT_USD_CHF);
		list.add(SPOT_USD_JPY);

		list.add(SPOT_BITCOIN);
		list.add(SPOT_ETHER);
		list.add(SPOT_RIPPLE);
		list.add(SPOT_LITECOIN);

		list.add(SPOT_FTSE_100);
		list.add(SPOT_DAX);
		list.add(SPOT_SP_500);
		list.add(SPOT_DOW_JONES);

		list.add(SPOT_GOLD);
		list.add(SPOT_SILVER);
		return list;
	}

	private final int marketId;
	private final IPricePrecision precision;
	private final InstrumentCategory type;

	public CityIndexInstrument(int marketId, InstrumentCategory type, IInstrument underlying, int decimalPlaces) {
		super(String.valueOf(marketId), underlying);
		this.marketId = marketId;
		this.precision = new PricePrecision(decimalPlaces);
		this.type = type;
	}

	@Override
	public int getMarketId() {
		return marketId;
	}

	@Override
	public InstrumentCategory getCategory() {
		return type;
	}

	@Override
	public IPricePrecision getPrecision() {
		return precision;
	}

	@Override
	public ITradingProvider getProvider() {
		return TradingProvider.IGINDEX;
	}

}
